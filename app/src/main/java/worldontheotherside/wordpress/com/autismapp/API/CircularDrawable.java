package worldontheotherside.wordpress.com.autismapp.API;

import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;
import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Created by زينب on 3/23/2018.
 */

public class CircularDrawable extends Drawable {

    private Paint paint;

    public CircularDrawable(Bitmap bitmap) {
        BitmapShader shader = new BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setShader(shader);
    }

    @Override
    public void draw(@NonNull Canvas canvas) {
        int height = getBounds().height();
        int width = getBounds().width();

        float radius;
        if(height <= width)
            radius = (float)height / 2;
        else
            radius = (float)width / 2;

        canvas.drawCircle((float)width/2, (float)height/2, radius, paint);
    }

    @Override
    public void setAlpha(int alpha) {
        paint.setAlpha(alpha);
    }

    @Override
    public void setColorFilter(@Nullable ColorFilter colorFilter) {
        paint.setColorFilter(colorFilter);
    }

    @Override
    public int getOpacity() {
        return PixelFormat.TRANSLUCENT;
    }
}
