package knight.nameless;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.ScreenUtils;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Starter extends ApplicationAdapter {
    private final int SCREEN_WIDTH = 1360;
    private final int SCREEN_HEIGHT = 768;
    private ShapeRenderer shapeRenderer;
    private SpriteBatch batch;
    private Rectangle rectangle;
    private BitmapFont font;

    @Override
    public void create() {
        shapeRenderer = new ShapeRenderer(); //Define the specific renderer for shapes
        rectangle = new Rectangle(SCREEN_WIDTH / 2f,SCREEN_HEIGHT / 2f,64,64); //Define rectangle

        batch = new SpriteBatch();
        font = new BitmapFont(Gdx.files.internal("fonts/test.fnt"));
        font.getData().scale(2f);
    }

    @Override
    public void render() {

        // Previous steps: Handle inputs and Update
        // Step 3 Draw: Set screen drawing color to black and clear the screen
        ScreenUtils.clear(Color.BLACK);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);

        // Set drawing color to white and draw the rectangle
        shapeRenderer.setColor(Color.WHITE);
        shapeRenderer.rect(rectangle.x, rectangle.y, rectangle.width, rectangle.height);

        shapeRenderer.end(); // Show everything in the screen

        batch.begin();
        font.draw(batch,"(366, 500)",100,800.0f/2.0f);
        batch.end();
    }

    @Override
    public void dispose() {

        font.dispose();
        shapeRenderer.dispose();
    }
}
