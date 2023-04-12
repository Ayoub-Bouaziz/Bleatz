package fr.stvenchg.bleatz.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import fr.stvenchg.bleatz.R;
import fr.stvenchg.bleatz.api.burger.BurgerResponse;
import fr.stvenchg.bleatz.api.menu.MenuResponse;

public class MenuAdapter extends ArrayAdapter<MenuResponse> {
    private Context context;
    private List<MenuResponse> menus;


    public MenuAdapter(Context context, List<MenuResponse> menus) {
        super(context, R.layout.layout_article, menus);
        this.context = context;
        this.menus = menus;
      ;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(R.layout.layout_article, null);

        // Récupération des vues
        ImageView imageArticle = convertView.findViewById(R.id.img_article);
        TextView titreArticle = convertView.findViewById(R.id.txt_titre_article);
        TextView descArticle = convertView.findViewById(R.id.txt_desc_article);
        TextView prixArticle = convertView.findViewById(R.id.txt_prix_article);

        // Récupération du menu courant
        MenuResponse menu = menus.get(position);

        // Récupération du burger correspondant au menu

/*
        // Chargement de l'image du burger à partir de l'URL
        Picasso.get().load(menu.getImage()).into(imageArticle);

        // Affichage du titre, de la description et du prix du menu
        titreArticle.setText(menu.getNomBurger());
        descArticle.setText(menu.getDescription());
        prixArticle.setText("$" + menu.getPrix());
*/
        return convertView;
    }
}

