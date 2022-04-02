package com.example.geradordegradeescolar.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.geradordegradeescolar.R;
import com.example.geradordegradeescolar.dao.DisciplinaDAO;
import com.example.geradordegradeescolar.model.Disciplina;

import java.util.ArrayList;
import java.util.List;

public class RecyclerDisciplinaAdapter extends RecyclerView.Adapter<RecyclerDisciplinaAdapter.MyViewHolder> {

    private final List<Disciplina> disciplinass = new ArrayList<>();
    private final Context context;
    private ArrayList<Disciplina> disciplinas = new ArrayList<>();

    public RecyclerDisciplinaAdapter(Context contexto) {
        this.context = contexto;
        DisciplinaDAO dao = new DisciplinaDAO();
        disciplinass.addAll(dao.todasDisciplinas());
    }

    public RecyclerDisciplinaAdapter(Context contexto, ArrayList<Disciplina> d){
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

        holder.exibirDisciplina.setOnClickListener(v -> {
            Intent intent = new Intent(context, FormDisciplina.class);
            intent.putExtra("disciplina", disciplinas.get(position));
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return disciplinas.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView disciplina;
        ConstraintLayout exibirDisciplina;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            disciplina = itemView.findViewById(R.id.textDisciplina);
            exibirDisciplina = itemView.findViewById(R.id.recyclerLayout);
        }
    }

    public void atualiza(List<Disciplina> disciplinas) {
        this.disciplinas.clear();
        this.disciplinas.addAll(disciplinas);
        notifyDataSetChanged();
    }
}
