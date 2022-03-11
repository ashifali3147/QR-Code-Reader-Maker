package com.example.qrcode;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Display;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.zxing.WriterException;

import androidmads.library.qrgenearator.QRGContents;
import androidmads.library.qrgenearator.QRGEncoder;
import androidmads.library.qrgenearator.QRGSaver;

public class MainActivity extends AppCompatActivity {

    EditText edtQRText;
    ImageView imgQRCode;
    ImageView imgGenerateQR, imgSave, imgQRScan;
    QRGEncoder qrgEncoder;
    Bitmap bitmap;
    String savePath = Environment.getExternalStorageDirectory().getPath();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edtQRText = (EditText) findViewById(R.id.edt_qrText);
        imgQRCode = (ImageView) findViewById(R.id.img_qrCode);
        imgGenerateQR = (ImageView) findViewById(R.id.img_qrGenerate);
        imgSave = (ImageView) findViewById(R.id.img_save);
        imgQRScan = (ImageView) findViewById(R.id.img_qrScan);

        imgGenerateQR.setOnClickListener(view -> {
            String text = edtQRText.getText().toString();
            if (text.length() > 0) {
                WindowManager manager = (WindowManager) getSystemService(WINDOW_SERVICE);
                Display display = manager.getDefaultDisplay();
                Point point = new Point();
                display.getSize(point);
                int width = point.x;
                int height = point.y;
                int smallerDimension = width < height ? width : height;
                smallerDimension = smallerDimension * 3 / 4;

                qrgEncoder = new QRGEncoder(
                        text, null,
                        QRGContents.Type.TEXT,
                        smallerDimension);

            try {
                bitmap = qrgEncoder.encodeAsBitmap();
                imgQRCode.setImageBitmap(bitmap);
            } catch (WriterException e) {
                Log.v("TAG", e.toString());
            }
        } else {
            edtQRText.setError("Required");
        }
    });
        imgSave.setOnClickListener(view -> {
            Toast.makeText(this, "Saved!", Toast.LENGTH_SHORT).show();
            boolean save;
            String result;
            try {
                save = QRGSaver.save(savePath, edtQRText.getText().toString().trim(), bitmap, QRGContents.ImageType.IMAGE_JPEG);
                result = save ? "Image Saved" : "Image Not Saved";
                Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG).show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        imgQRScan.setOnClickListener(view -> {
            startActivity(new Intent(MainActivity.this, BarcodeScanner.class));
        });

    }
}

/*/

[Today Work]

1. Project setup on a different gradle version.
2. Try to understand project code.
3. Create an app for QR Code implementation.
4. Implement QR Code Generator.
5. Save QR code in local storage.
6. Learn about camera permission.
7. Implement Surface view for camera.
8. Create a function for QR code scanner.
 */