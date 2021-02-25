package plugin.response;

import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

public class BookResponseTest extends AbstractResponseParserTest {
    private final String defaultOutput = "I've found a Frankenstein's monster (Fictitious character) -- Fiction book called Frankenstein; Or, The Modern Prometheus, written by Shelley, Mary Wollstonecraft. Would you like me to read it to you?\n";
    // Metadata parsing occurs separately from message parsing so we can test them
    // separately.

    @Before
    public void setName() {
        setServiceName("book");
    }

    // Testing different messages

    /**
     * The default message defined in the JSON file.
     */
    @Test
    public void defaultMessage() {
        check(serviceName, structure, defaultOutput, metadata, false);
    }

    // Testing different metadata

    /**
     * Defining empty metadata structure should return an empty map in the response.
     */
    @Test
    public void noMetadata() {
        structure.put("metadata", new HashMap<>());
        check(serviceName, structure, defaultOutput, metadata, true);
    }

    /**
     * A valid metadata field with no custom alias.
     */
    @Test
    public void bookIdInMetadataNoAlias() {
        Map<String, Object> metadataStructure = new HashMap<>();
        metadataStructure.put("results[0].id", "results[0].id");
        structure.put("metadata", metadataStructure);
        metadata.put("results[0].id", 84);
        check(serviceName, structure, defaultOutput, metadata, true);
    }

    /**
     * A valid metadata field with a custom alias using the alias shorthand syntax.
     */
    @Test
    public void bookIdInMetadataWithAliasShorthand() {
        Map<String, Object> metadataStructure = new HashMap<>();
        metadataStructure.put("results[0].id", "id");
        structure.put("metadata", metadataStructure);
        metadata.put("id", 84);
        check(serviceName, structure, defaultOutput, metadata, true);
    }

    /**
     * A valid metadata field with a custom alias.
     */
    @Test
    public void bookIdInMetadataWithAlias() {
        Map<String, Object> metadataStructure = new HashMap<>();
        Map<String, Object> field = new HashMap<>();
        field.put("alias", "id");
        metadataStructure.put("results[0].id", field);
        structure.put("metadata", metadataStructure);
        metadata.put("id", 84);
        check(serviceName, structure, defaultOutput, metadata, true);
    }

    /**
     * Defining a custom map in the metadata with valid children.
     */
    @Test
    public void customMetadataWithChildren() {
        Map<String, Object> metadataStructure = new HashMap<>();
        Map<String, Object> field = new HashMap<>();
        field.put("custom", true);

        Map<String, Object> children = new HashMap<>();
        children.put("results[0].id", "id");
        field.put("children", children);

        metadataStructure.put("data", field);
        structure.put("metadata", metadataStructure);

        Map<String, Object> data = new HashMap<>();
        data.put("id", 84);
        metadata.put("data", data);
        check(serviceName, structure, defaultOutput, metadata, true);
    }

    /**
     * Defining a custom map in the metadata without specifying any children.
     */
    @Test
    public void customMetadataNoChildren() {
        Map<String, Object> metadataStructure = new HashMap<>();
        Map<String, Object> field = new HashMap<>();
        field.put("custom", true);

        metadataStructure.put("data", field);
        structure.put("metadata", metadataStructure);

        check(serviceName, structure, defaultOutput, metadata, true);
    }

    /**
     * Defining a custom map in the metadata without the children attribute.
     */
    @Test
    public void customMetadataEmptyChildren() {
        Map<String, Object> metadataStructure = new HashMap<>();
        Map<String, Object> field = new HashMap<>();
        field.put("custom", true);

        Map<String, Object> children = new HashMap<>();
        field.put("children", children);
        metadataStructure.put("data", field);
        structure.put("metadata", metadataStructure);

        Map<String, Object> data = new HashMap<>();
        metadata.put("data", data);
        check(serviceName, structure, defaultOutput, metadata, true);
    }

    /**
     * Defining the children attribute is redundant if the parameter is not marked as custom.
     */
    @Test
    public void customMetadataWithChildrenNoFlag() {
        Map<String, Object> metadataStructure = new HashMap<>();
        Map<String, Object> field = new HashMap<>();

        Map<String, Object> children = new HashMap<>();
        children.put("results[0].id", "id");
        field.put("children", children);

        metadataStructure.put("data", field);
        structure.put("metadata", metadataStructure);

        metadata.put("data", null);
        check(serviceName, structure, defaultOutput, metadata, true);
    }
}
