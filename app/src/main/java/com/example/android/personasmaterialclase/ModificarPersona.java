package com.example.android.personasmaterialclase;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

public class ModificarPersona extends AppCompatActivity {
    private ImageView fotoModificar;
    private EditText txtNombreModificar;
    private EditText txtApellidoModificar;
    private EditText txtCedulaModificar;
    private Intent i;
    private Bundle b;
    private StorageReference storageReference;
    private Uri uri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modificar_persona);

        fotoModificar = (ImageView)findViewById(R.id.fotoModificar);
        txtCedulaModificar = (EditText)findViewById(R.id.txtCedulaModificar);
        txtNombreModificar = (EditText)findViewById(R.id.txtNombreModificar);
        txtApellidoModificar = (EditText)findViewById(R.id.txtApellidoModificar);
        storageReference = FirebaseStorage.getInstance().getReference();


        i = getIntent();
        b = i.getBundleExtra("datos");

        txtCedulaModificar.setText(b.getString("cedula"));
        txtNombreModificar.setText(b.getString("nombre"));
        txtApellidoModificar.setText(b.getString("apellido"));

        storageReference.child(b.getString("foto"))
                .getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.with(ModificarPersona.this).load(uri).into(fotoModificar);
            }
        });


    }

    public void modificar(View v){
        String cedula = txtCedulaModificar.getText().toString();
        String nombre = txtNombreModificar.getText().toString();
        String apellido = txtApellidoModificar.getText().toString();
        Persona p = new Persona(b.getString("id"),b.getString("foto"),cedula,
                nombre,apellido);

        if(cedula.equals(b.getString("cedula"))){
            p.modificar();
            if(uri!=null)subir_foto(b.getString("foto"));
            Snackbar.make(v, R.string.mensaje_persona_modificada_exitosamente,Snackbar.LENGTH_SHORT)
                    .setAction("action",null).show();


        }else{
            if(Metodos.existencia_persona(Datos.obtenerPersonas(),cedula)){
                txtCedulaModificar.setError(getString(R.string.mensaje_error_cedula_existente));

                txtCedulaModificar.requestFocus();
            }else{
                p.modificar();
                if(uri!=null)subir_foto(b.getString("foto"));
                Snackbar.make(v, R.string.mensaje_persona_modificada_exitosamente,Snackbar.LENGTH_SHORT)
                        .setAction("action",null).show();

            }
        }
    }

    public void eliminar(View v){
        String positivo, negativo;

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getString(R.string.eliminar));
        builder.setMessage(R.string.mensaje_ventana_eliminar);
        positivo=getString(R.string.opcion_positivo);
        negativo=getString(R.string.opcion_negativo);

        builder.setPositiveButton(positivo, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Persona p = new Persona(b.getString("id"));
                p.eliminar();
                onBackPressed();
            }
        });

        builder.setNegativeButton(negativo, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void onBackPressed(){
        finish();
        Intent i = new Intent(ModificarPersona.this,Principal.class);
        startActivity(i);
    }

    public void seleccionar_foto(View v){
        Intent i = new Intent();
        i.setType("image/*");
        i.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(i,getString(R.string.mensaje_seleccion_imagen)),1);
    }

    protected void onActivityResult(int requesCode, int resultCode, Intent data){
        super.onActivityResult(requesCode,resultCode,data);
        if(requesCode==1){
            uri = data.getData();
            if(uri!=null){
                fotoModificar.setImageURI(uri);
            }
        }
    }

    public void subir_foto(String foto){
        StorageReference childRef = storageReference.child(foto);
        UploadTask uploadTask = childRef.putFile(uri);
        uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                onBackPressed();
            }
        });
    }

}
