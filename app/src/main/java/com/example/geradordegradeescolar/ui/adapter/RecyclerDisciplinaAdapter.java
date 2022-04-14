package com.example.geradordegradeescolar.ui.adapter;

import android.content.Context;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.geradordegradeescolar.R;
import com.example.geradordegradeescolar.model.Disciplina;

import java.util.List;

public class RecyclerDisciplinaAdapter extends RecyclerView.Adapter<RecyclerDisciplinaAdapter.MyViewHolder> {

    private final Context context;
    private final List<Disciplina> disciplinas;

    public RecyclerDisciplinaAdapter(Context contexto, List<Disciplina> d) {
        this.context = contexto;
        this.disciplinas = d;
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
        holder.disciplina.setText(disciplinas.get(position).toString());
    }

    @Override
    public int getItemCount() {
        return disciplinas.size();
    }

    public Disciplina getItem(int position) {
        return disciplinas.get(position);
    }

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
        notifyDataSetChanged();
    }

    public void atualiza(List<Disciplina> disciplinas) {
        this.disciplinas.clear();
        this.disciplinas.addAll(disciplinas);
        notifyDataSetChanged();
    }
}
