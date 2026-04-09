package knight.nameless;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
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
    private Rectangle rectangle;
    private BitmapFont font;
    private Texture fontTexture;

    @Override
    public void create() {

        shapeRenderer = new ShapeRenderer(); //Define the specific renderer for shapes
        rectangle = new Rectangle(SCREEN_WIDTH / 2f,SCREEN_HEIGHT / 2f,64,64); //Define rectangle

        batch = new SpriteBatch();
        fontTexture = new Texture("fonts/test.png");
        fontTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        font = new BitmapFont(Gdx.files.internal("fonts/test.fnt"), new TextureRegion(fontTexture));
        font.getData().scale(2f);

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
            rectangle.setPosition(mouseBounds.x, mouseBounds.y);
    }

    @Override
    public void render() {

        touchControllers();

        ScreenUtils.clear(Color.BLACK);

        shapeRenderer.setProjectionMatrix(viewport.getCamera().combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);

        shapeRenderer.setColor(Color.WHITE);
        shapeRenderer.rect(rectangle.x, rectangle.y, rectangle.width, rectangle.height);

        shapeRenderer.end();

        batch.setProjectionMatrix(viewport.getCamera().combined);
        batch.begin();
        font.draw(batch, "(" + (int) rectangle.x + ", " + (int) rectangle.y + ")", 450, SCREEN_HEIGHT - 25);
        batch.end();
    }

    @Override
    public void dispose() {

        font.dispose();
        fontTexture.dispose();
        shapeRenderer.dispose();
        batch.dispose();
    }
}
