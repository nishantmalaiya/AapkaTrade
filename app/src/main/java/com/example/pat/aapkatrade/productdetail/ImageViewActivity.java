package com.example.pat.aapkatrade.productdetail;//package com.example.pat.aapkatrade.productdetail;
//
//import android.annotation.SuppressLint;
//import android.graphics.Matrix;
//import android.graphics.PointF;
//import android.support.v7.app.AppCompatActivity;
//import android.os.Bundle;
//import android.util.FloatMath;
//import android.view.MotionEvent;
//import android.view.View;
//import android.widget.ImageView;
//
//import com.example.pat.aapkatrade.R;
//
//public class ImageViewActivity extends AppCompatActivity
//{
//
//    ImageView imageDetail;
//    Matrix matrix = new Matrix();
//    Matrix savedMatrix = new Matrix();
//    PointF startPoint = new PointF();
//    PointF midPoint = new PointF();
//    float oldDist = 1f;
//    static final int NONE = 0;
//    static final int DRAG = 1;
//    static final int ZOOM = 2;
//    int mode = NONE;
//
//
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState)
//    {
//        super.onCreate(savedInstanceState);
//
//        setContentView(R.layout.activity_image_view);
//
//        imageDetail = (ImageView) findViewById(R.id.imageView1);
//        /**
//         * set on touch listner on image
//         */
//        imageDetail.setOnTouchListener(new View.OnTouchListener()
//        {
//
//            @Override
//            public boolean onTouch(View v, MotionEvent event)
//            {
//
//                ImageView view = (ImageView) v;
//                System.out.println("matrix=" + savedMatrix.toString());
//                switch (event.getAction() & MotionEvent.ACTION_MASK) {
//                    case MotionEvent.ACTION_DOWN:
//                        savedMatrix.set(matrix);
//                        startPoint.set(event.getX(), event.getY());
//                        mode = DRAG;
//                        break;
//
//                    case MotionEvent.ACTION_POINTER_DOWN:
//                        oldDist = spacing(event);
//                        if (oldDist > 10f)
//                        {
//                            savedMatrix.set(matrix);
//                            midPoint(midPoint, event);
//                            mode = ZOOM;
//                        }
//                        break;
//
//                    case MotionEvent.ACTION_UP:
//
//                    case MotionEvent.ACTION_POINTER_UP:
//                        mode = NONE;
//
//                        break;
//
//                    case MotionEvent.ACTION_MOVE:
//                        if (mode == DRAG)
//                        {
//                            matrix.set(savedMatrix);
//                            matrix.postTranslate(event.getX() - startPoint.x,
//                                    event.getY() - startPoint.y);
//                        }
//                        else if (mode == ZOOM)
//                        {
//                            float newDist = spacing(event);
//                            if (newDist > 10f)
//                            {
//                                matrix.set(savedMatrix);
//                                float scale = newDist / oldDist;
//                                matrix.postScale(scale, scale, midPoint.x, midPoint.y);
//                            }
//                        }
//                        break;
//
//                }
//                view.setImageMatrix(matrix);
//
//                return true;
//            }
//
//            @SuppressLint("FloatMath")
//            private float spacing(MotionEvent event)
//            {
//                float x = event.getX(0) - event.getX(1);
//                float y = event.getY(0) - event.getY(1);
//                return FloatMath.sqrt(x * x + y * y);
//            }
//
//            private void midPoint(PointF point, MotionEvent event)
//            {
//                float x = event.getX(0) + event.getX(1);
//                float y = event.getY(0) + event.getY(1);
//                point.set(x / 2, y / 2);
//
//            }
//
//
//        });
//
//    }
//}
