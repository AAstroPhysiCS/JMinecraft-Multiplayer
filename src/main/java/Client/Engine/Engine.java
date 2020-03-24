package Client.Engine;

import Client.Engine.Attributes.AntiAliasing;
import Client.Engine.Screen.Screen;
import Client.Events.KeyboardEvent;
import Client.Events.MouseCursorEvent;
import Client.Events.MouseEvent;
import Client.Game.World.World;
import org.joml.Vector3f;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL.createCapabilities;
import static org.lwjgl.opengl.GL13.*;

public class Engine {

    private Screen screen;
    private AntiAliasing aliasing;

    private World world;

    private boolean vSync;

    private Thread thread;
    private static boolean running;

    public Engine(Screen screen, boolean vSync, AntiAliasing aliasing) {
        this.screen = screen;
        this.vSync = vSync;
        this.aliasing = aliasing;
        thread = new Thread(run(), "Main Thread");
    }

    public void start() {
        thread.start();
        running = true;
    }

    private void init() {
        if (!glfwInit()) {
            System.err.println("Could not initialize GLFW");
        }

        glfwDefaultWindowHints();
        glfwWindowHint(GLFW_RESIZABLE, GLFW_FALSE);
        glfwWindowHint(GLFW_SAMPLES, aliasing.getLevel());

        screen.setId(glfwCreateWindow(Screen.getWidth(), Screen.getHeight(), screen.getTitle(), 0, 0));
        glfwMakeContextCurrent(screen.getId());
        glfwSetMouseButtonCallback(screen.getId(), new MouseEvent());
        glfwSetCursorPosCallback(screen.getId(), new MouseCursorEvent());
        glfwSetKeyCallback(screen.getId(), new KeyboardEvent());
        glfwShowWindow(screen.getId());
        glfwSwapInterval(vSync ? 1 : 0);

        createCapabilities();
        glEnable(GL_MULTISAMPLE);
        glEnable(GL_DEPTH_TEST);
        glActiveTexture(GL_TEXTURE1);
    }

    private void update() {
        world.update();

        if (KeyboardEvent.getKeys()[GLFW_KEY_ESCAPE])
            dispose();

        if (MouseEvent.getButtons()[GLFW_MOUSE_BUTTON_1])
            glfwSetInputMode(screen.getId(), GLFW_CURSOR, GLFW_CURSOR_DISABLED);

        glfwPollEvents();
    }

    private void render() {
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
        glClearColor(187 / 255f, 215 / 255f, 254 / 255f, 1.0f);
        world.render();
        glfwSwapBuffers(screen.getId());
    }

    private void dispose() {
        glfwTerminate();
        System.gc();
        System.exit(0);
    }

    private Runnable run() {
        return () -> {
            init();

            world = new World();

            long time = System.currentTimeMillis();
            int frames = 0;
            while (running) {
                update();
                render();

                frames++;

                if (System.currentTimeMillis() - time > 1000) {
                    time += 1000;
                    glfwSetWindowTitle(screen.getId(), "AAstr0Craft - Multiplayer | FPS: " + frames);
                    frames = 0;
                    Vector3f pos = world.getPlayer().getCamera().getPosition();
                    System.out.println("Player X: " + pos.x + ", Y: " + pos.y + ", Z: " + pos.z);
                }

                int error = glGetError();
                if (error != GL_NO_ERROR)
                    System.err.println(error);
            }
        };
    }

    public World getWorld() {
        return world;
    }
}
