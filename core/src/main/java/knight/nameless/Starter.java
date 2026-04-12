package knight.nameless;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ExtendViewport;

public class Starter extends ApplicationAdapter {
    private final int SCREEN_WIDTH = 1360;
    private final int SCREEN_HEIGHT = 768;
    public OrthographicCamera camera;
    public ExtendViewport viewport;
    private ShapeRenderer shapeRenderer;
    private SpriteBatch batch;
    private Texture playerTexture;
    private Rectangle playerBounds;
    private Rectangle ball;
    private Vector2 ballVelocity;
    private BitmapFont font;
    private Texture fontTexture;
    private Sound sound;
    private Color[] colors;
    private int colorIndex;
    private int score;

    @Override
    public void create() {

        batch = new SpriteBatch();
        shapeRenderer = new ShapeRenderer();

        fontTexture = new Texture("fonts/test.png");
        fontTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        font = new BitmapFont(Gdx.files.internal("fonts/test.fnt"), new TextureRegion(fontTexture));
        font.getData().scale(2f);

        sound = Gdx.audio.newSound(Gdx.files.internal("sounds/magic.wav"));

        playerTexture = new Texture("img/redbird.png");
        playerBounds = new Rectangle(
            SCREEN_WIDTH / 2f,
            SCREEN_HEIGHT / 2f,
            playerTexture.getWidth() * 2,
            playerTexture.getHeight() * 2
        );

        ball = new Rectangle(100, 100, 32, 32);
        ballVelocity = new Vector2(400, 400);

        colors = new Color[]{
            Color.RED,
            Color.GREEN,
            Color.BLUE,
            Color.CORAL,
            Color.GOLD,
            Color.CYAN,
            Color.FOREST,
            Color.PURPLE,
            Color.LIGHT_GRAY,
            Color.VIOLET,
        };

        camera = new OrthographicCamera();
        camera.position.set(SCREEN_WIDTH / 2f, SCREEN_HEIGHT / 2f, 0);
        viewport = new ExtendViewport(SCREEN_WIDTH, SCREEN_HEIGHT, camera);
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
    }

    private void touchControllers() {

        Vector3 worldCoordinates = camera.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0));
        var mouseBounds = new Rectangle(worldCoordinates.x, worldCoordinates.y, 2, 2);

        if (Gdx.input.isTouched())
            playerBounds.setPosition(mouseBounds.x, mouseBounds.y);
    }

    private void keyboardControllers(float deltaTime) {

        final int playerSpeed = 600;

        if (Gdx.app.getInput().isKeyPressed(Input.Keys.W) && playerBounds.y < SCREEN_HEIGHT - playerBounds.height)
            playerBounds.y += playerSpeed * deltaTime;

        if (Gdx.app.getInput().isKeyPressed(Input.Keys.S) && playerBounds.y > 0)
            playerBounds.y -= playerSpeed * deltaTime;

        if (Gdx.app.getInput().isKeyPressed(Input.Keys.D) && playerBounds.x < SCREEN_WIDTH - playerBounds.width)
            playerBounds.x += playerSpeed * deltaTime;

        if (Gdx.app.getInput().isKeyPressed(Input.Keys.A) && playerBounds.x > 0)
            playerBounds.x -= playerSpeed * deltaTime;
    }

    private void update() {

        float deltaTime = Gdx.graphics.getDeltaTime();

        keyboardControllers(deltaTime);

        touchControllers();

        if (ball.x < 0 || ball.x > SCREEN_WIDTH - ball.width) {

            ballVelocity.x *= -1;
            colorIndex = MathUtils.random(0, colors.length - 1);
        }
        else if (ball.y < 0 || ball.y > SCREEN_HEIGHT - ball.height) {

            ballVelocity.y *= -1;
            colorIndex = MathUtils.random(0, colors.length - 1);
        }

        if (playerBounds.overlaps(ball)) {

            ballVelocity.scl(-1);
            score++;
            sound.play();
        }

        if (ball.x > SCREEN_WIDTH || ball.x < - ball.width || ball.y > SCREEN_HEIGHT || ball.y < - ball.height) {

            ball.x = SCREEN_WIDTH / 2f;
            ball.y = SCREEN_HEIGHT / 2f;
            ballVelocity.scl(-1);
        }

        ball.x += (int)(ballVelocity.x * deltaTime);
        ball.y += (int)(ballVelocity.y * deltaTime);
    }

    @Override
    public void render() {

        update();

        ScreenUtils.clear(Color.BLACK);

        shapeRenderer.setProjectionMatrix(viewport.getCamera().combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);

        shapeRenderer.setColor(Color.WHITE);

        shapeRenderer.setColor(colors[colorIndex]);
        shapeRenderer.rect(ball.x, ball.y, ball.width, ball.height);

        shapeRenderer.end();

        batch.setProjectionMatrix(viewport.getCamera().combined);
        batch.begin();

        batch.draw(playerTexture, playerBounds.x, playerBounds.y, playerBounds.width, playerBounds.height);

        int fps = Gdx.graphics.getFramesPerSecond();
        font.draw(batch, String.valueOf(fps), 150, SCREEN_HEIGHT - 25);
        font.draw(batch, String.valueOf(score), SCREEN_WIDTH / 2f - 150, SCREEN_HEIGHT - 25);

        batch.end();
    }

    @Override
    public void dispose() {

        font.dispose();
        playerTexture.dispose();
        fontTexture.dispose();
        sound.dispose();
        shapeRenderer.dispose();
        batch.dispose();
    }
}
