package com.example.android.personasmaterialclase;

import android.content.Context;
import android.content.res.Resources;
import android.net.Uri;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by android on 17/10/2017.
 */

public class AdaptadorPersona extends RecyclerView.Adapter<AdaptadorPersona.PersonaViewHolder> {
    private ArrayList<Persona> personas;
    private Resources res;
    private Context contexto;
    private OnPersonaClickListener clickListener;


    public AdaptadorPersona(Context contexto, ArrayList<Persona> personas,
                            OnPersonaClickListener clickListener){
        this.personas=personas;
        this.res=contexto.getResources();
        this.contexto=contexto;
        this.clickListener=clickListener;
    }


    @Override
    public PersonaViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
       View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_persona,parent,false);
        return new PersonaViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final PersonaViewHolder holder, int position) {
            final Persona p = personas.get(position);

        StorageReference storageReference = FirebaseStorage.getInstance().getReference();
        storageReference.child(p.getFoto()).getDownloadUrl()
                .addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.with(contexto).load(uri).into(holder.foto);
            }
        });
            holder.cedula.setText(p.getCedula());
            holder.nombre.setText(p.getNombre());
            holder.apellido.setText(p.getApellido());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickListener.onPersonaClick(p);
            }
        });

    }

    @Override
    public int getItemCount() {
        return personas.size();
    }

    public static class PersonaViewHolder extends RecyclerView.ViewHolder{
        private ImageView foto;
        private TextView cedula;
        private TextView nombre;
        private TextView apellido;


        public PersonaViewHolder(View item){
            super(item);

            foto = (ImageView)item.findViewById(R.id.imgFoto);
            cedula = (TextView) item.findViewById(R.id.lblCedula);
            nombre = (TextView) item.findViewById(R.id.lblNombre);
            apellido = (TextView) item.findViewById(R.id.lblApellido);
        }

    }

    public interface OnPersonaClickListener{
        void onPersonaClick(Persona p);
    }

}
