package com.example.geradordegradeescolar.ui.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.geradordegradeescolar.R;
import com.example.geradordegradeescolar.model.Disciplina;

import java.util.List;

public class RecyclerGradeAdapter extends RecyclerView.Adapter<RecyclerGradeAdapter.MyViewHolder> {

    private final Context contexto;
    private final List<Disciplina> grade;

    public RecyclerGradeAdapter(Context contexto, List<Disciplina> grade) {
        this.contexto = contexto;
        this.grade = grade;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(contexto);
        View infalte = inflater.inflate(R.layout.item_grade, parent, false);
        return new MyViewHolder(infalte);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull RecyclerGradeAdapter.MyViewHolder holder, int position) {
        holder.disciplina.setText(grade.get(position).getNome());
        holder.diaDaSemana.setText("Dia: " + grade.get(position).getDiaSemana());
    }

    @Override
    public int getItemCount() {
        return grade.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        final TextView diaDaSemana;
        final TextView disciplina;
        final ConstraintLayout exibirGrade;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            disciplina = itemView.findViewById(R.id.textGrade);
            exibirGrade = itemView.findViewById(R.id.gradeRecycler);
            diaDaSemana = itemView.findViewById(R.id.textDiaGrade);
        }
    }
}
