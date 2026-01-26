package com.dv.trunov.game.renderer;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.dv.trunov.game.model.Ball;
import com.dv.trunov.game.model.BallParticle;
import com.dv.trunov.game.model.Platform;
import com.dv.trunov.game.util.Constants;

import java.util.List;

public class ObjectRenderer {

    private static final ObjectRenderer INSTANCE = new ObjectRenderer();

    private ObjectRenderer() {
    }

    public static ObjectRenderer getInstance() {
        return INSTANCE;
    }

    public void drawBorder(ShapeRenderer shapeRenderer) {
        Color main = new Color(0.28f, 0.15f, 0.32f, 1f);
        Color back = new Color(0.28f, 0.15f, 0.32f, 0.25f);
        float thickness = 2f;
        shapeRenderer.setColor(main);
        shapeRenderer.rect(
            Constants.Border.LEFT,
            Constants.Border.GAME_FIELD_TOP,
            Constants.Border.RIGHT - Constants.Border.LEFT,
            thickness
        );
        shapeRenderer.rect(
            Constants.Border.LEFT,
            Constants.Border.BOTTOM,
            Constants.Border.RIGHT - Constants.Border.LEFT,
            thickness
        );

        shapeRenderer.setColor(back);
        shapeRenderer.rect(
            Constants.Border.LEFT,
            Constants.Border.GAME_FIELD_TOP,
            Constants.Border.RIGHT - Constants.Border.LEFT,
            thickness + 2f
        );
        shapeRenderer.rect(
            Constants.Border.LEFT,
            Constants.Border.BOTTOM - 1f,
            Constants.Border.RIGHT - Constants.Border.LEFT,
            thickness + 2f
        );
    }

    public void drawPlatforms(Platform[] platforms, SpriteBatch batch) {
        for (Platform platform : platforms) {
            batch.draw(
                platform.getTexture(),
                platform.getX(),
                platform.getY(),
                platform.getWidth(),
                platform.getHeight()
            );
        }
    }

    public void drawBall(Ball ball, SpriteBatch batch) {
        batch.draw(
            ball.getTexture(),
            ball.getX() - ball.getRadius(),
            ball.getY() - ball.getRadius(),
            ball.getRadius() * 2,
            ball.getRadius() * 2
        );
    }

    public void drawBallTail(Ball ball, SpriteBatch batch) {
        List<Vector2> trailPoints = ball.getTrailPoints();
        int trailsNumber = trailPoints.size();
        for (int index = 0; index < trailsNumber; index++) {
            Vector2 trail = trailPoints.get(index);
            float lastIndex = (float) (index + 1) / trailsNumber;
            float scale = 0.4f + 0.6f * lastIndex;
            float alpha = 0.2f * lastIndex;
            float renderSize = ball.getRadius() * 2 * scale;
            batch.setColor(1f, 1f, 1f, alpha);
            batch.draw(ball.getTexture(), trail.x - renderSize / 2f, trail.y - renderSize / 2f, renderSize, renderSize);
        }
    }

    public void drawBallExplosion(Ball ball, SpriteBatch batch) {
        for (BallParticle particle : ball.getParticles()) {
            float time = particle.getNormalizedAge();
            float size = particle.getSize();
            float alpha = 1f - time;
            float x = particle.getPosition().x;
            float y = particle.getPosition().y;
            batch.setColor(1f, 1f, 1f, alpha);
            batch.draw(ball.getTexture(), x - size / 2f, y - size / 2f, size, size);
        }
        batch.setColor(1f, 1f, 1f, 1f);
    }
}
