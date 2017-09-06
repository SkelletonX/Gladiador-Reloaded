package me.skelletonx.br.GladiadorReloaded;

import java.util.ArrayList;
import java.util.HashMap;
import org.bukkit.entity.Player;

public class VariaveisGlobais {

	public boolean isNoite;
    public boolean isMitoEnable = true;
    public boolean isGladiadorEnable = true;
    public boolean isChuvendo;
    public boolean isOcorrendo;
    public boolean isAberto;
    public ArrayList<Player> todosParticipantes = new ArrayList<Player>();
    public HashMap<String, Integer> clans = new HashMap<String, Integer>();
    public int quantMensagens;
    public int precoParaParticipar;
    public int premioParaCada;
    public int premioParaLider;
    public int limiteDeMembros;
    public int quantGladiadores;
    public int quantGladiadoresAdicionados;
    
    public void resetVariaveis(){
        isOcorrendo = false;
        isAberto = false;
        todosParticipantes.clear();
        clans.clear();
        premioParaCada = Gladiador.getGladiador().getConfig().getInt("Gladiador_Premio.Money_Para_Cada_Integrante");
        premioParaLider = Gladiador.getGladiador().getConfig().getInt("Gladiador_Premio.Money_Para_Cada_Lider");
        isNoite = Gladiador.getGladiador().getConfig().getBoolean("Gladiador.Deixar_De_Noite");
        isChuvendo = Gladiador.getGladiador().getConfig().getBoolean("Gladiador.Retirar_Chuva");
        quantMensagens = Gladiador.getGladiador().getConfig().getInt("Gladiador.Anuncio_Quantidade");
        precoParaParticipar = Gladiador.getGladiador().getConfig().getInt("Gladiador.Preco_Para_Entrar");
        limiteDeMembros = Gladiador.getGladiador().getConfig().getInt("Gladiador.Limite_De_Membros");
        quantGladiadores = Gladiador.getGladiador().getConfig().getInt("Gladiador_Tag.Quantidade");
        quantGladiadoresAdicionados = 0;
    }
    
    public void resetVariaveisSemIS(){
        todosParticipantes.clear();
        clans.clear();
        quantMensagens = Gladiador.getGladiador().getConfig().getInt("Gladiador.Anuncio_Quantidade");
        precoParaParticipar = Gladiador.getGladiador().getConfig().getInt("Gladiador.Preco_Para_Entrar");
        limiteDeMembros = Gladiador.getGladiador().getConfig().getInt("Gladiador.Limite_De_Membros");
        quantGladiadores = Gladiador.getGladiador().getConfig().getInt("Gladiador_Tag.Quantidade");
        quantGladiadoresAdicionados = 0;
    }
    
}