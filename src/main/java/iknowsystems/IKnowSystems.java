package iknowsystems;

public class IKnowSystems {

    private final String id;
    private final String name;
    private final String tags;
    private final String text;

    public IKnowSystems(String id, String name, String tags, String text) {
        this.id = id;
        this.name = name;
        this.tags = tags;
        this.text = text;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getTags() {
        return tags;
    }

    public String getText() {
        return text;
    }
}
