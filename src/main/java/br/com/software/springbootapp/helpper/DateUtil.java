package br.com.software.springbootapp.helpper;

import java.time.format.DateTimeFormatter;

public class DateUtil {

    public static DateTimeFormatter dtfCompleto = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss SSS");
    public static DateTimeFormatter dtfDataBrasil = DateTimeFormatter.ofPattern("dd/MM/yyyy");

}
