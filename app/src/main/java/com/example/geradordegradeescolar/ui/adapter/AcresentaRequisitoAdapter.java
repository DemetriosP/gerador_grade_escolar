package com.example.geradordegradeescolar.ui.adapter;

import android.content.Context;
import android.graphics.Color;
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

import java.util.ArrayList;
import java.util.List;

public class AcresentaRequisitoAdapter extends RecyclerView.Adapter<AcresentaRequisitoAdapter.MyViewHolder> {

    private final Context contexto;
    private final List<Disciplina> requisitos;
    private final List<Disciplina> requisitosSelecionados = new ArrayList<>();
    boolean isSelectMode = false;

    public AcresentaRequisitoAdapter(Context contexto, List<Disciplina> requisitos) {
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

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView requisito;
        ConstraintLayout exibirRequisito;
        CardView cardRequisito;

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
