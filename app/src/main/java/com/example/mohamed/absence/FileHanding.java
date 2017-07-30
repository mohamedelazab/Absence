package com.example.mohamed.absence;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Environment;
import android.support.v7.app.AlertDialog;
import android.text.InputType;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by mohamed on 09/04/17.
 */

public class FileHanding {

    public static String fileName;
    public static File file;
    Context c;
    StringBuffer stringBuffer;
    public static boolean tvFileState;

    public FileHanding(Context c) {
        this.c =c;
    }


    public void createFile(String fileName){
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)){

            File root =Environment.getExternalStorageDirectory();
            File dir =new File(root.getAbsolutePath()+"/AbsenceData");
            if (!dir.exists()){
                dir.mkdir();
            }

            file =new File(dir,(fileName+".txt"));
            Log.d("Hamdy","file creation.!");
        }
        else {
            Toast.makeText(c, "SD card not found.!", Toast.LENGTH_SHORT).show();
        }
        Log.d("Hamdy","file creation finished.!");
    }

    //write text from nfc tag to the file
    public void writeInFile(String record){

            try {
                FileOutputStream fos = new FileOutputStream(file, true);
                String xx =readFromFile(record);
                if (!xx.contains(record)){
                    fos.write(record.getBytes());
                    Toast.makeText(c, "File Created.!", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(c, "Student Already added.!", Toast.LENGTH_SHORT).show();
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

    }

    //read texts from file to prevent repeating insertion..
    public String readFromFile(String record){

        try {
            FileInputStream fis =new FileInputStream(file);
            InputStreamReader inputStreamReader =new InputStreamReader(fis);
            BufferedReader bufferedReader =new BufferedReader(inputStreamReader);
            stringBuffer =new StringBuffer();
            while ((record =bufferedReader.readLine())!=null){
                stringBuffer.append(record+"\n");
            }


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringBuffer.toString();
    }

    //default file name
    @TargetApi(Build.VERSION_CODES.N)
    public String defaultFileName() {

        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());
        return "Absence "+timeStamp;
    }

    //creating file dialog
    public String dialogView(){
        fileName =defaultFileName();
        AlertDialog.Builder builder = new AlertDialog.Builder(c);
        builder.setTitle("File Name");

        final EditText input = new EditText(c);
        input.setText(defaultFileName());

        input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_WEB_EDIT_TEXT);
        builder.setView(input);

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //creating file to external storage
                fileName = input.getText().toString();
                createFile(fileName);
                MainActivity.tvFileName.setText("File selected, you can write now.!");
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                MainActivity.tvFileName.setText("");

            }
        });

        builder.show();
        return MainActivity.tvFileName.getText().toString();
    }

}
