package com.pacha.proyecto.service.db;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import com.pacha.proyecto.model.Cultivo;
import com.pacha.proyecto.model.DatosPronostico;
import com.pacha.proyecto.model.Fenologia;
import com.pacha.proyecto.model.Umbrales;
import com.pacha.proyecto.repository.CultivoRepository;
import com.pacha.proyecto.repository.DatosPronosticoRepository;
import com.pacha.proyecto.repository.FenologiaRepository;
import com.pacha.proyecto.repository.UmbralRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
@Primary
public class DatosPronosticoServiceJpa {

    @Autowired
    private DatosPronosticoRepository datosPronosticoRepo;

    @Autowired
    private CultivoRepository cultivoRepository;

    @Autowired
    private FenologiaRepository fenologiaRepository;

    @Autowired
    private UmbralRepository umbralRepository;

    @Autowired
    private DatosPronosticoRepository pronosticoRepository;

    public Map<String, String> generarUltimaAlerta(int cultivoId) {
        Cultivo cultivo = cultivoRepository.findById(cultivoId)
                .orElseThrow(() -> new ResourceNotFoundException2("Cultivo no encontrado"));
        Date fechaSiembra = cultivo.getFechaSiembra();
        List<Fenologia> fenologias = fenologiaRepository.findByIdCultivoOrdered(cultivoId);
        List<DatosPronostico> pronosticos = pronosticoRepository.findByIdCultivo(cultivo.getIdCultivo());
        Map<String, String> alertas = new HashMap<>();
        float pcpnAcumulada = 0;
        Map<Fenologia, Float> pcpnPorFase = new HashMap<>();

        for (DatosPronostico pronostico : pronosticos) {
            Fenologia faseActual = null;
            Date fechaInicioFase = fechaSiembra;
            float pcpnAcumuladaHastaFaseActual = 0;

            for (int i = 0; i < fenologias.size(); i++) {
                Fenologia fenologia = fenologias.get(i);
                Date fechaFinFase;
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(fechaInicioFase);
                calendar.add(Calendar.DAY_OF_YEAR, fenologia.getNroDias());
                fechaFinFase = calendar.getTime();
                Optional<Umbrales> optionalUmbral = umbralRepository.findByIdFenologia(fenologia.getIdFenologia());
                if (optionalUmbral.isPresent()) {
                    Umbrales umbral = optionalUmbral.get();
                    pcpnAcumuladaHastaFaseActual += umbral.getPcpn();
                    System.out.println("Acumulación actual de precipitación hasta fase '" + fenologia.getFase() + "': "
                            + pcpnAcumuladaHastaFaseActual);
                } else {
                    System.out.println("No se encontró umbral para la fenología con ID " + fenologia.getIdFenologia());
                }
                if (!pronostico.getFechaRangoDecenal().before(fechaInicioFase)
                        && !pronostico.getFechaRangoDecenal().after(fechaFinFase)) {
                    faseActual = fenologia;
                    System.out.println("Fase actual encontrada: " + faseActual.getFase() + " con fecha pronóstico "
                            + pronostico.getFechaRangoDecenal());
                    break;
                }
                fechaInicioFase = fechaFinFase;
            }

            System.out.println("Precipitación acumulada total hasta la fase actual: " + pcpnAcumuladaHastaFaseActual);

            if (faseActual != null) {
                pcpnAcumulada += pronostico.getPcpn();
                pcpnPorFase.put(faseActual, pcpnPorFase.getOrDefault(faseActual, 0f) + pronostico.getPcpn());
                Optional<Umbrales> optionalUmbral = umbralRepository.findByIdFenologia(faseActual.getIdFenologia());
                List<Object[]> normales = fenologiaRepository.obtenerNormal(faseActual.getIdFenologia());
                float pcpnNormal = 0;
                if (!normales.isEmpty()) {
                    pcpnNormal = (float) normales.get(0)[0];
                }
                if (optionalUmbral.isPresent()) {
                    Umbrales umbral = optionalUmbral.get();
                    if (pronostico.getTempMax() > umbral.getTempMax()) {
                        alertas.put("TempMax", "Alerta ROJA: TempMax pronostico: " + pronostico.getTempMax()
                                + " supera el TempMax del umbral: " + umbral.getTempMax() + " en la fase "
                                + faseActual.getFase());
                    } else if (pronostico.getTempMax() > umbral.getUmbSup()) {
                        alertas.put("TempMax", "Alerta AMARILLA: TempMax pronostico: " + pronostico.getTempMax()
                                + " supera el UmbSup del umbral: " + umbral.getUmbSup() + " en la fase "
                                + faseActual.getFase());
                    } else if (pronostico.getTempMax() <= umbral.getTempOptMax()) {
                        alertas.put("TempMax", "Alerta VERDE: TempMax pronostico: " + pronostico.getTempMax()
                                + " esta dentro del rango optimo: " + umbral.getTempOptMax() + " en la fase "
                                + faseActual.getFase());
                    }
                    if (pronostico.getTempMin() < umbral.getTempMin()) {
                        alertas.put("TempMin", "Alerta ROJA: TempMin pronostico: " + pronostico.getTempMin()
                                + " esta por debajo del TempMin del umbral: " + umbral.getTempMin() + " en la fase "
                                + faseActual.getFase());
                    } else if (pronostico.getTempMin() < umbral.getUmbInf()) {
                        alertas.put("TempMin", "Alerta AMARILLA: TempMin pronostico: " + pronostico.getTempMin()
                                + " esta por debajo del UmbInf del umbral: " + umbral.getUmbInf() + " en la fase "
                                + faseActual.getFase());
                    } else if (pronostico.getTempMin() >= umbral.getTempOptMin()) {
                        alertas.put("TempMin", "Alerta VERDE: TempMin pronostico: " + pronostico.getTempMin()
                                + " esta dentro del rango optimo: " + umbral.getTempOptMin() + " en la fase "
                                + faseActual.getFase());
                    }
                    float pcpnAcumuladaFaseActual = pcpnPorFase.get(faseActual);
                    pcpnAcumuladaHastaFaseActual = Math.round(pcpnAcumuladaHastaFaseActual * 100.0f) / 100.0f;
                    double diferencia = Math.abs(pcpnAcumulada - pcpnAcumuladaHastaFaseActual);

                    if (diferencia >= 50) {
                        alertas.put("Pcpn", "Alerta ROJA: Pcpn acumulada: " + pcpnAcumulada +
                                " esta por debajo de la Pcpn umbral: " + pcpnAcumuladaHastaFaseActual + " en la fase "
                                + faseActual.getFase());
                    } else if (diferencia >= 26) {
                        System.out.println("Entrando a la alerta amarilla");
                        alertas.put("Pcpn", "Alerta AMARILLA: Pcpn acumulada: " + pcpnAcumuladaFaseActual +
                                " está cerca de la Pcpn umbral: " + pcpnAcumuladaHastaFaseActual + " en la fase "
                                + faseActual.getFase());
                    } else {
                        alertas.put("Pcpn", "Alerta VERDE: Pcpn acumulada: " + pcpnAcumuladaFaseActual +
                                " es menor que la Pcpn umbral: " + pcpnAcumuladaHastaFaseActual + " en la fase "
                                + faseActual.getFase());
                    }

                } else {
                    alertas.put("General", "No se encontro umbral para la fenologia " + faseActual.getFase());
                }
            } else {
                alertas.put("General", "No se encontro fase fenologica para el pronostico en la fecha "
                        + pronostico.getFechaRangoDecenal());
            }
        }
        for (Map.Entry<Fenologia, Float> entry : pcpnPorFase.entrySet()) {
            alertas.put("PcpnFase" + entry.getKey().getFase(), "Precipitacion acumulada en la fase "
                    + entry.getKey().getFase() + ": " + entry.getValue());
        }
        alertas.put("PcpnGeneral", "Precipitacion acumulada total hasta la fase actual: " + pcpnAcumulada);

        return alertas;
    }

    public List<DatosPronostico> pronosticosFase(int cultivoId) {
        Cultivo cultivo = cultivoRepository.findById(cultivoId)
                .orElseThrow(() -> new ResourceNotFoundException2("Cultivo no encontrado"));
        Date fechaSiembra = cultivo.getFechaSiembra();
        List<Fenologia> fenologias = fenologiaRepository.findByIdCultivoOrdered(cultivoId);
        List<DatosPronostico> pronosticos = pronosticoRepository.findByIdCultivo(cultivo.getIdCultivo());

        List<DatosPronostico> pronosticosRecolectados = new ArrayList<>();
        int fase = 0;
        Fenologia ultimaFaseEncontrada = null;

        for (Fenologia fenologia : fenologias) {
            Date fechaInicioFase = fechaSiembra;
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(fechaInicioFase);
            calendar.add(Calendar.DAY_OF_YEAR, fenologia.getNroDias());
            Date fechaFinFase = calendar.getTime();
            List<DatosPronostico> pronosticosFaseActual = new ArrayList<>();

            for (DatosPronostico pronostico : pronosticos) {

                if (!pronostico.getFechaRangoDecenal().before(fechaInicioFase)
                        && !pronostico.getFechaRangoDecenal().after(fechaFinFase)) {
                    System.out.println("Fase actual encontrada: " + fenologia.getFase() +
                            " con fecha pronóstico "
                            + pronostico.getFechaRangoDecenal());
                    pronosticosFaseActual.add(pronostico);
                    fase = fenologia.getFase();
                }
            }

            if (!pronosticosFaseActual.isEmpty()) {
                ultimaFaseEncontrada = fenologia;
                pronosticosRecolectados.addAll(pronosticosFaseActual);
            }
            fechaSiembra = fechaFinFase;
        }
        return pronosticosRecolectados;
    }

    public int obtenerFaseActual(int cultivoId) {
        Cultivo cultivo = cultivoRepository.findById(cultivoId)
                .orElseThrow(() -> new ResourceNotFoundException2("Cultivo no encontrado"));
        Date fechaSiembra = cultivo.getFechaSiembra();
        List<Fenologia> fenologias = fenologiaRepository.findByIdCultivoOrdered(cultivoId);
        List<DatosPronostico> pronosticos = pronosticoRepository.findByIdCultivo(cultivo.getIdCultivo());
        int fase = 0;
        Fenologia ultimaFaseEncontrada = null;

        for (Fenologia fenologia : fenologias) {
            Date fechaInicioFase = fechaSiembra;
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(fechaInicioFase);
            calendar.add(Calendar.DAY_OF_YEAR, fenologia.getNroDias());
            Date fechaFinFase = calendar.getTime();
            List<DatosPronostico> pronosticosFaseActual = new ArrayList<>();

            for (DatosPronostico pronostico : pronosticos) {

                if (!pronostico.getFechaRangoDecenal().before(fechaInicioFase)
                        && !pronostico.getFechaRangoDecenal().after(fechaFinFase)) {
                    System.out.println("Fase actual encontrada: " + fenologia.getFase() + " con fecha pronóstico "
                            + pronostico.getFechaRangoDecenal());
                    fase = fenologia.getFase();
                }
            }

            if (!pronosticosFaseActual.isEmpty()) {
                ultimaFaseEncontrada = fenologia;
            }
            fechaSiembra = fechaFinFase;
        }
        return fase;
    }

    public List<Map<String, Object>> generarPcpnFase(int cultivoId) {
        Cultivo cultivo = cultivoRepository.findById(cultivoId)
                .orElseThrow(() -> new ResourceNotFoundException2("Cultivo no encontrado"));
        Date fechaSiembra = cultivo.getFechaSiembra();
        List<Fenologia> fenologias = fenologiaRepository.findByIdCultivo(cultivoId);
        List<DatosPronostico> pronosticos = pronosticoRepository.findByIdCultivo(cultivo.getIdCultivo());
        float pcpnAcumulada = 0;
        Map<Fenologia, Float> pcpnPorFase = new HashMap<>();
        for (DatosPronostico pronostico : pronosticos) {
            Fenologia faseActual = null;
            Date fechaInicioFase = fechaSiembra;
            for (int i = 0; i < fenologias.size(); i++) {
                Fenologia fenologia = fenologias.get(i);
                Date fechaFinFase;
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(fechaInicioFase);
                calendar.add(Calendar.DAY_OF_YEAR, fenologia.getNroDias());
                fechaFinFase = calendar.getTime();
                if (!pronostico.getFecha().before(fechaInicioFase) && !pronostico.getFecha().after(fechaFinFase)) {
                    faseActual = fenologia;
                    break;
                }
                fechaInicioFase = fechaFinFase;
            }

            if (faseActual != null) {
                pcpnAcumulada += pronostico.getPcpn();
                pcpnPorFase.put(faseActual, pcpnPorFase.getOrDefault(faseActual, 0f) + pronostico.getPcpn());
            }
        }

        List<Map<String, Object>> pcpnFaseList = new ArrayList<>();
        for (Map.Entry<Fenologia, Float> entry : pcpnPorFase.entrySet()) {
            Map<String, Object> map = new HashMap<>();
            map.put("fase", entry.getKey().getFase());
            map.put("pcpnAcumulada", entry.getValue() != null ? entry.getValue() : 0f);
            pcpnFaseList.add(map);
            System.out.println("faseee " + entry.getKey().getFase());
            System.out.println("pcpnaccc " + entry.getValue());
        }
        Map<String, Object> totalMap = new HashMap<>();
        totalMap.put("fase", "Total");
        totalMap.put("pcpnAcumulada", pcpnAcumulada);
        pcpnFaseList.add(totalMap);

        return pcpnFaseList;
    }

    public void editarPronostico(DatosPronostico request) {
        Optional<DatosPronostico> existingRecord = datosPronosticoRepo.findByIdPronostico(request.getIdPronostico());

        if (existingRecord.isPresent()) {
            DatosPronostico pronostico = existingRecord.get();
            pronostico.setTempMax(request.getTempMax());
            pronostico.setTempMin(request.getTempMin());
            pronostico.setPcpn(request.getPcpn());
            pronostico.setFecha(request.getFecha());
            pronostico.setEdit(true);
            pronostico.setDelete(false);
            datosPronosticoRepo.save(pronostico);
        } else {
            throw new EntityNotFoundException("Registro no encontrado con el ID: " + request.getIdPronostico());
        }
    }

    public boolean eliminarPronostico(int idpronostico) {
        Optional<DatosPronostico> datosPronosticoOptional = datosPronosticoRepo.findByIdPronostico(idpronostico);
        if (datosPronosticoOptional.isPresent()) {
            DatosPronostico datosPronostico = datosPronosticoOptional.get();
            datosPronostico.setDelete(true);
            datosPronosticoRepo.save(datosPronostico);
            return true;
        } else {
            return false;
        }
    }

    public int contarDatosHoy(int idZona) {
        return datosPronosticoRepo.countDatosHoy(idZona);
    }

}
