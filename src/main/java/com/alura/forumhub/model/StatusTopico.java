package com.alura.forumhub.model; // No mesmo pacote da classe Topico

public enum StatusTopico {
    NAO_RESPONDIDO, // Tópico que ainda não recebeu nenhuma resposta
    NAO_SOLUCIONADO, // Tópico que recebeu respostas, mas a dúvida ainda não foi solucionada
    SOLUCIONADO,    // Tópico cuja dúvida foi resolvida
    FECHADO;        // Tópico que foi fechado e não permite mais interações (opcional, mas útil)
}