package com.praktikumpab.android_client;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder> {
    private List<User> userList;

    public UserAdapter(List<User> userList) {
        this.userList = userList;
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_user, parent, false);
        return new UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        User user = userList.get(position);
        holder.textViewNIM.setText(user.getNIM());
        holder.textViewName.setText(user.getName());
        holder.textViewProgstud.setText(user.getProgstud());
        holder.textViewEmail.setText(user.getEmail());
        holder.textViewAlamat.setText(user.getAlamat());
        holder.textViewTanggalLahir.setText(user.getTanggalLahir());
        holder.textViewJenisKelamin.setText(user.getJenisKelamin());
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    public static class UserViewHolder extends RecyclerView.ViewHolder {
        TextView textViewNIM;
        TextView textViewName;
        TextView textViewProgstud;
        TextView textViewEmail;
        TextView textViewAlamat;
        TextView textViewTanggalLahir;
        TextView textViewJenisKelamin;

        public UserViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewNIM = itemView.findViewById(R.id.textViewNIM);
            textViewName = itemView.findViewById(R.id.textViewName);
            textViewProgstud = itemView.findViewById(R.id.textViewProgstud);
            textViewEmail = itemView.findViewById(R.id.textViewEmail);
            textViewAlamat = itemView.findViewById(R.id.textViewAlamat);
            textViewTanggalLahir = itemView.findViewById(R.id.textViewTanggalLahir);
            textViewJenisKelamin = itemView.findViewById(R.id.textViewJenisKelamin);
        }
    }
}
