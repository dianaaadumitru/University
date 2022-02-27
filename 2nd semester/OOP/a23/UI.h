#pragma once
#include "service.h"

typedef struct {
    Service *holidays_service;
}Holidays_UI;

Holidays_UI* create_UI(Service * service);

void run(Holidays_UI * holidays_ui);

void destroy_ui(Holidays_UI * holiday_ui);