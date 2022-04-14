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

public class RecyclerRequisitoAdapter extends RecyclerView.Adapter<RecyclerRequisitoAdapter.MyViewHolder> {

    private final Context contexto;
    private final List<Disciplina> requisitos;

    public RecyclerRequisitoAdapter(Context contexto, List<Disciplina> requisitos) {
        this.contexto = contexto;
        this.requisitos = requisitos;
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
        holder.requisito.setText(requisitos.get(position).getNome());
    }

    @Override
    public int getItemCount() {
        return requisitos.size();
    }

    public Disciplina getItem(int position){
        return requisitos.get(position);
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener{

        TextView requisito;
        ConstraintLayout exibirRequisito;
        CardView cardRequisito;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            requisito = itemView.findViewById(R.id.textRequisito);
            exibirRequisito = itemView.findViewById(R.id.recyclerRequisito);
            cardRequisito = itemView.findViewById(R.id.cardRequisito);
            cardRequisito.setOnCreateContextMenuListener(this);
        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            menu.add(this.getAdapterPosition(), 1, 0, "Remover");
        }
    }

    public void remove(Disciplina disciplina) {
        requisitos.remove(disciplina);
        notifyDataSetChanged();
    }

    public void atualiza(List<Disciplina> disciplinas) {
        this.requisitos.clear();
        this.requisitos.addAll(disciplinas);
        notifyDataSetChanged();
    }
}
