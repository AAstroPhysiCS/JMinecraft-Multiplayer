package Client.Engine.Screen;

public class Screen {

    private static int WIDTH, HEIGHT;
    private long id;
    private final String title;

    public Screen(final int WIDTH, final int HEIGHT, String title){
        Screen.WIDTH = WIDTH;
        Screen.HEIGHT = HEIGHT;
        this.title = title;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public static int getHeight() {
        return HEIGHT;
    }

    public static int getWidth() {
        return WIDTH;
    }

    public String getTitle() {
        return title;
    }
}
