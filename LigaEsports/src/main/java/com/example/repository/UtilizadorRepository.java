package com.example.repository;

import com.example.domain.Utilizador;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class UtilizadorRepository {

    private static final String FILE_NAME = "utilizadores.ser";
    private List<Utilizador> utilizadores;

    public UtilizadorRepository() {
        this.utilizadores = carregar();
    }

    public void salvar(Utilizador u) {
        utilizadores.add(u);
        gravar();
    }

    public void remover(Double id) {
        utilizadores.removeIf(u -> u.getId().equals(id));
        gravar();
    }

    public List<Utilizador> listarTodos() {
        return utilizadores;
    }

    private void gravar() {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            out.writeObject(utilizadores);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    private List<Utilizador> carregar() {
        File f = new File(FILE_NAME);
        if (!f.exists()) return new ArrayList<>();

        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
            return (List<Utilizador>) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
}
