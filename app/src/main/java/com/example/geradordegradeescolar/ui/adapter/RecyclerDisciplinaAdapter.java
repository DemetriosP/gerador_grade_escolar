package com.example.geradordegradeescolar.ui.adapter;

import android.content.Context;
import android.view.ContextMenu;
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

public class RecyclerDisciplinaAdapter extends RecyclerView.Adapter<RecyclerDisciplinaAdapter.MyViewHolder> implements Filterable {

    private final Context context;
    private final List<Disciplina> disciplinas;
    private List<Disciplina> disciplinasPesquisa;

    public RecyclerDisciplinaAdapter(Context contexto, List<Disciplina> d) {
        this.context = contexto;
        this.disciplinas = d;
        this.disciplinasPesquisa = new ArrayList<>(disciplinas);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View inflate = inflater.inflate(R.layout.item_disciplina, parent, false);
        return new MyViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        disciplinas.sort(Comparator.comparing(Disciplina::getNome));
        holder.disciplina.setText(disciplinas.get(position).toString());
    }

    @Override
    public int getItemCount() {
        return disciplinas.size();
    }

    public Disciplina getItem(int position) {
        return disciplinas.get(position);
    }

    @Override
    public Filter getFilter() {
        return disciplinasFiltro;
    }

    public Filter disciplinasFiltro = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<Disciplina> disciplinasFiltradas = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {
                disciplinasFiltradas.addAll(disciplinasPesquisa);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();

                for (Disciplina disciplina : disciplinasPesquisa) {

                    if (disciplina.getNome().toLowerCase().contains(filterPattern)) {
                        disciplinasFiltradas.add(disciplina);
                    }
                }
            }

            FilterResults resultados = new FilterResults();
            resultados.values = disciplinasFiltradas;

            return resultados;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            disciplinas.clear();
            disciplinas.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };

    public static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener {

        TextView disciplina;
        ConstraintLayout exibirDisciplina;
        CardView cardDisciplina;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            disciplina = itemView.findViewById(R.id.textDisciplina);
            exibirDisciplina = itemView.findViewById(R.id.recyclerLayoutDisciplina);
            cardDisciplina = itemView.findViewById(R.id.cardDisciplina);
            cardDisciplina.setOnCreateContextMenuListener(this);
        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            menu.add(this.getAdapterPosition(), 1, 0, "Requisitos");
            menu.add(this.getAdapterPosition(), 2, 1, "Editar");
            menu.add(this.getAdapterPosition(), 3, 2, "Remover");
        }

    }


    public void remove(Disciplina disciplina) {
        disciplinas.remove(disciplina);
        disciplinasPesquisa.remove(disciplina);
        notifyDataSetChanged();
    }

    public void atualiza(List<Disciplina> disciplinas) {
        this.disciplinas.clear();
        this.disciplinas.addAll(disciplinas);
        this.disciplinasPesquisa.clear();
        this.disciplinasPesquisa = new ArrayList<>(disciplinas);
        notifyDataSetChanged();
    }
}
