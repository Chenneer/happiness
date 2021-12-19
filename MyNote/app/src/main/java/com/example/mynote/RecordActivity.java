package com.example.mynote;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.BaseColumns;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;

public class RecordActivity extends AppCompatActivity implements View.OnClickListener{
    TextView note_time;
    EditText content;
    private SQLiteHelper mSQLiteHelper;
    private String id;
    private String  photoPath;
    private ImageView imageView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record);
        note_time=(TextView)findViewById(R.id.tv_time);//保存记录的时间
        content=(EditText) findViewById(R.id.note_content);//记录的内容
        imageView=findViewById(R.id.image);
        initData();


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(new String[]{Manifest.permission.CAMERA,Manifest.permission.READ_EXTERNAL_STORAGE},10);
        }
        note_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



            }
        });
        imageView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                imageView.setImageBitmap(null);
                photoPath=null;
                return false;
            }
        });

    }
    public void initData(){
        mSQLiteHelper=new SQLiteHelper(this);
        setTitle("添加记录");
        Intent intent=getIntent();
        if(intent!=null){
            id=intent.getStringExtra("id");
            if(id!=null){
                setTitle("修改记录");
                content.setText(intent.getStringExtra("content"));
                note_time.setText(intent.getStringExtra("time"));
                photoPath=(intent.getStringExtra("photo"));
                note_time.setVisibility(View.VISIBLE);
                if(!TextUtils.isEmpty(photoPath)){
                    imageView.setVisibility(View.VISIBLE);
                    imageView.setImageBitmap(BitmapFactory.decodeFile(photoPath));
                }
            }
        }
    }
    public void showToast(String message){
        Toast.makeText(RecordActivity.this,message,Toast.LENGTH_LONG).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.detail,menu);

        id=getIntent().getStringExtra("id");

        if(id==null){
            menu.findItem(R.id.delete).setVisible(false);
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.take_photo:
                Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
                startActivityForResult(intent,12);

                break;
            case R.id.add_photo:
                openAlbum();
                break;
            case R.id.delete:
                AlertDialog dialog;
                AlertDialog.Builder builder=new AlertDialog.Builder(RecordActivity.this)
                        .setMessage("是否删除此记录?")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if(mSQLiteHelper.deleteData(id)){
                                    Toast.makeText(RecordActivity.this,"删除成功",Toast.LENGTH_LONG).show();
                                    finish();
                                }

                            }
                        })
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                dialog=builder.create();
                dialog.show();

                break;
            case R.id. save:
                String noteContent =content.getText().toString().trim();
                if(id!=null){
                    //修改记录的功能
                    if(noteContent.length()>0){
                        if (mSQLiteHelper.updateData(id,noteContent,DBUtils.getTime(),photoPath)){
                            showToast("修改成功");
                            setResult(2);
                            finish();
                        }else{
                            showToast("修改失败");
                        }
                    } else{
                        showToast("修改的记录内容不能为空");
                    }
                }else{
                    //添加记录的功能
                    if(noteContent.length()>0){
                        if (mSQLiteHelper.insertData(noteContent,DBUtils.getTime(),photoPath)){
                            showToast("保存成功");
                            setResult(2);
                            finish();
                        }else{
                            showToast("保存失败");
                        }
                    } else{
                        showToast("保存的记录内容不能为空");
                    }
                }
                break;

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {



        return super.onContextItemSelected(item);
    }


    @Override
    public void onClick(View v) {

    }



    void openAlbum(){
        Intent intent=new Intent("android.intent.action.GET_CONTENT");
        //把所有照片显示出来
        intent.setType("image/*");
        startActivityForResult(intent,123);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode==1&&grantResults.length>0&&grantResults[0]== PackageManager.PERMISSION_GRANTED)
            openAlbum();
        else Toast.makeText(this, "你拒绝了打开相册的权限", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //当选择完相片，就会回到这里，然后相片的相关信息会保存在data中，后面想办法取出来
        String tar=  "/data/data/"+getPackageName()+"/"+System.currentTimeMillis()+".jpg";

        if (requestCode==123){

            //通过getData方法取得它的图片地址，后面的操作都是对这个地址的解析
            Uri uri=data.getData();
            String realPathFromUriAboveApi19 = getRealPathFromUriAboveApi19(getApplicationContext(), uri);
            if(new File(realPathFromUriAboveApi19).exists()){


                FileUtil.copyFile(realPathFromUriAboveApi19,tar);

                photoPath=tar;
                imageView.setVisibility(View.VISIBLE);
                imageView.setImageBitmap(BitmapFactory.decodeFile(realPathFromUriAboveApi19));
            }
        }else if(requestCode==12){
            if(resultCode==RESULT_OK) {
                Bundle bundle = data.getExtras();
                Bitmap bitmap = (Bitmap) bundle.get("data");
                FileUtil.saveBitmap(tar, bitmap);
                photoPath = tar;
                imageView.setImageBitmap(bitmap);
            }
        }
    }

    /**
     * 适配api19及以上,根据uri获取图片的绝对路径
     *
     * @param context 上下文对象
     * @param uri     图片的Uri
     * @return 如果Uri对应的图片存在, 那么返回该图片的绝对路径, 否则返回null
     */
    private static String getRealPathFromUriAboveApi19(Context context, Uri uri) {
        String filePath = null;
        if (DocumentsContract.isDocumentUri(context, uri)) {
            // 如果是document类型的 uri, 则通过document id来进行处理
            String documentId = DocumentsContract.getDocumentId(uri);
            if (isMediaDocument(uri)) { // MediaProvider
                final String[] divide = documentId.split(":");
                final String type = divide[0];
                Uri mediaUri = null;
                if ("image".equals(type)) {
                    mediaUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    mediaUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    mediaUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                } else {
                    return null;
                }
                String selection = BaseColumns._ID + "=?";
                String[] selectionArgs = {divide[1]};
                filePath = getDataColumn(context, mediaUri, selection, selectionArgs);
            } else if (isDownloadsDocument(uri)) { // DownloadsProvider
                Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(documentId));
                filePath = getDataColumn(context, contentUri, null, null);
            }else if(isExternalStorageDocument(uri)) {
                String [] split = documentId.split(":");
                if(split.length >= 2) {
                    String type = split[0];
                    if("primary".equalsIgnoreCase(type)) {
                        filePath = Environment.getExternalStorageDirectory() + "/" + split[1];
                    }
                }
            }
        } else if (ContentResolver.SCHEME_CONTENT.equalsIgnoreCase(uri.getScheme())){
            // 如果是 content 类型的 Uri
            filePath = getDataColumn(context, uri, null, null);
        } else if (ContentResolver.SCHEME_FILE.equals(uri.getScheme())) {
            // 如果是 file 类型的 Uri,直接获取图片对应的路径
            filePath = uri.getPath();
        }
        return filePath;
    }

    /**
     * 获取数据库表中的 _data 列，即返回Uri对应的文件路径
     * @return
     */
    private static String getDataColumn(Context context, Uri uri, String selection, String[] selectionArgs) {
        String path = null;

        String[] projection = new String[]{MediaStore.Images.Media.DATA};
        Cursor cursor = null;
        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs, null);
            if (cursor != null && cursor.moveToFirst()) {
                int columnIndex = cursor.getColumnIndexOrThrow(projection[0]);
                path = cursor.getString(columnIndex);
            }
        } catch (Exception e) {
            if (cursor != null) {
                cursor.close();
            }
        }
        return path;
    }

    /**
     * @param uri the Uri to check
     * @return Whether the Uri authority is MediaProvider
     */
    private static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri the Uri to check
     * @return Whether the Uri authority is DownloadsProvider
     */
    private static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    private static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

}
