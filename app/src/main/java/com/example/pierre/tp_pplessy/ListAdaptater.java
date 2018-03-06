package com.example.pierre.tp_pplessy;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class ListAdaptater extends ArrayAdapter<EcolePrimaire> {

    public ListAdaptater(Context context, List<EcolePrimaire> ecoles) {
        super(context, 0, ecoles);
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.row_list_ecole,parent, false);
        }

        listViewHolder viewHolder = (listViewHolder) convertView.getTag();
//        création de la vue
        if(viewHolder == null){
            viewHolder = new listViewHolder();
            viewHolder.nom = convertView.findViewById(R.id.nom);
            viewHolder.nbEleve = convertView.findViewById(R.id.nbEleve);
            viewHolder.addresse = convertView.findViewById(R.id.addresse);
            convertView.setTag(viewHolder);
        }

        //récupére l'index  de l'école
        EcolePrimaire ecole = getItem(position);

        //remplisage de la vue
        viewHolder.nom.setText(ecole.getNom());
        viewHolder.nbEleve.setText(ecole.getNbEleve());
        viewHolder.addresse.setText(ecole.getAddresse());

        return convertView;
    }

    class listViewHolder {
        public TextView nom;
        public TextView nbEleve;
        public TextView addresse;
    }

}
