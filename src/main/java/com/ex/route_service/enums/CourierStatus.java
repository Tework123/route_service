package com.ex.route_service.enums;


public enum CourierStatus {
    READY,       // Курьер готов получить заказ)
    BUSY,           // выполняет заказ
    PAUSED,       // Временно неактивен (перерыв, тех. пауза)
    FINISHED,    // Завершил смену
//    ABORTED       // Прервал смену досрочно (ошибка, ЧС и т.п.)

}