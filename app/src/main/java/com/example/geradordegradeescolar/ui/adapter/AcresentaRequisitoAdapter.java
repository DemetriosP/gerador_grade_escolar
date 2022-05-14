package com.example.geradordegradeescolar.ui.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.geradordegradeescolar.R;
import com.example.geradordegradeescolar.model.Disciplina;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class AcresentaRequisitoAdapter extends RecyclerView.Adapter<AcresentaRequisitoAdapter.MyViewHolder> implements Filterable {

    private final Context contexto;
    private final List<Disciplina> requisitos;
    private final List<Disciplina> requisitosPesquisa;
    private final List<Disciplina> requisitosSelecionados = new ArrayList<>();
    boolean isSelectMode = false;

    public AcresentaRequisitoAdapter(Context contexto, List<Disciplina> requisitos) {
        this.contexto = contexto;
        this.requisitos = requisitos;
        this.requisitosPesquisa = new ArrayList<>(requisitos);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(contexto);
        View inflate = inflater.inflate(R.layout.item_requisito, parent, false);
        return new MyViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        requisitos.sort(Comparator.comparing(Disciplina::getNome));
        holder.requisito.setText(requisitos.get(position).getNome());

        holder.cardRequisito.setOnLongClickListener(v -> {

            isSelectMode = true;
            if (requisitosSelecionados.contains(requisitos.get(position))) {
                v.setBackgroundColor(Color.TRANSPARENT);
                requisitosSelecionados.remove(requisitos.get(position));
            } else {
                v.setBackgroundResource(R.color.gray);
                requisitosSelecionados.add(requisitos.get(position));
            }

            if (requisitosSelecionados.size() == 0) isSelectMode = false;
            return true;
        });

        holder.cardRequisito.setOnClickListener(v -> {
            if (isSelectMode) {
                if (requisitosSelecionados.contains(requisitos.get(position))) {
                    v.setBackgroundColor(Color.TRANSPARENT);
                    requisitosSelecionados.remove(requisitos.get(position));
                } else {
                    v.setBackgroundResource(R.color.gray);
                    requisitosSelecionados.add(requisitos.get(position));
                }

                if (requisitosSelecionados.size() == 0) isSelectMode = false;
            }
        });
    }

    @Override
    public int getItemCount() {
        return requisitos.size();
    }

    @Override
    public Filter getFilter() {
        return requisitosFiltro;
    }

    public final Filter requisitosFiltro = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<Disciplina> requisitosFiltrados = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {
                requisitosFiltrados.addAll(requisitosPesquisa);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();

                for (Disciplina requisito : requisitosPesquisa) {

                    if (requisito.getNome().toLowerCase().contains(filterPattern)) {
                        requisitosFiltrados.add(requisito);
                    }
                }
            }

            FilterResults resultados = new FilterResults();
            resultados.values = requisitosFiltrados;

            return resultados;
        }

        @SuppressLint("NotifyDataSetChanged")
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            requisitos.clear();
            requisitos.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };


    public static class MyViewHolder extends RecyclerView.ViewHolder {

        final TextView requisito;
        final ConstraintLayout exibirRequisito;
        final CardView cardRequisito;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            requisito = itemView.findViewById(R.id.textRequisito);
            exibirRequisito = itemView.findViewById(R.id.recyclerRequisito);
            cardRequisito = itemView.findViewById(R.id.cardRequisito);

        }
    }

    public List<Disciplina> getRequisitosSelecionados() {
        return requisitosSelecionados;
    }

}
