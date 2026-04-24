package br.com.AutonomousAPI.mappers;

public class Datamask {

        public static String maskCpf(String cpf) {
            if (cpf == null || cpf.length() != 11) return "+-+-+-+-+-+";
            return cpf.substring(0, 2) + "+-+-+-+" + cpf.substring(9);
        }
}
