
package com.airtribe.trustdesk.knowledge.config;

import com.airtribe.trustdesk.knowledge.entity.KnowledgeDocument;
import com.airtribe.trustdesk.knowledge.repository.KnowledgeDocumentRepository;

import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
@Order(4)
public class KnowledgeBaseDataLoader implements CommandLineRunner {

    private final KnowledgeDocumentRepository knowledgeRepository;

    public KnowledgeBaseDataLoader(
            KnowledgeDocumentRepository knowledgeRepository) {
        this.knowledgeRepository = knowledgeRepository;
    }

    @Override
    public void run(String... args) throws Exception {

        PathMatchingResourcePatternResolver resolver =
                new PathMatchingResourcePatternResolver();

        Resource[] resources = resolver.getResources(
                "classpath*:data/knowledge_base/*.md"
        );

        int importedCount = 0;

        for (Resource resource : resources) {

            try (InputStream inputStream = resource.getInputStream()) {

                String content = new String(
                        inputStream.readAllBytes(),
                        StandardCharsets.UTF_8
                );

                String docId = extractMetadata(content, "Doc ID");

                if (docId == null || docId.isBlank()) {
                    System.out.println(
                            "Skipping document: Missing Doc ID - "
                                    + resource.getFilename()
                    );
                    continue;
                }

                if (knowledgeRepository.existsById(docId)) {
                    continue;
                }

                String title = extractTitle(content);

                String audience = extractMetadata(
                        content, "Audience"
                );

                String version = extractMetadata(
                        content, "Version"
                );

                KnowledgeDocument document =
                        new KnowledgeDocument();

                document.setDocId(docId);
                document.setTitle(title);
                document.setContent(content);
                document.setSourcePath(
                        "data/knowledge_base/" + resource.getFilename()
                );
                document.setVersion(version);
                document.setAudience(audience);
                document.setUpdatedAt(LocalDateTime.now());

                knowledgeRepository.save(document);

                importedCount++;

                System.out.println(
                        "Imported Knowledge Document: " + docId
                );
            }
        }

        System.out.println(
                "Knowledge Base seed import completed. New records: "
                        + importedCount
        );
    }

    private String extractMetadata(String content, String key) {

        Pattern pattern = Pattern.compile(
                "(?im)^\\s*" + Pattern.quote(key)
                        + "\\s*:\\s*(.+)$"
        );

        Matcher matcher = pattern.matcher(content);

        if (matcher.find()) {
            return matcher.group(1).trim();
        }

        return null;
    }

    private String extractTitle(String content) {

        Pattern pattern = Pattern.compile(
                "(?m)^#\\s+(.+)$"
        );

        Matcher matcher = pattern.matcher(content);

        if (matcher.find()) {
            return matcher.group(1).trim();
        }

        return "Untitled Knowledge Document";
    }
}
