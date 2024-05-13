workspace {

    model {

        # people
        traveler = person "Podróżujący"
        BOK = person "Biuro obsługi klienta" "Dział zajmujący się obsługą klienta"
        supervisor = person "TripMaker supervisor" "Funkcja zarządzająca w TripMaker"
        travelAgent = person "Agent podróży"
        hotelOwner = person "Właściciel hotelu"

        # internal systems
        tripMaker = softwareSystem "TripMaker" "System do planowania podróży" {
            backend_tripMaker = container "TripMaker Backend" "Modularny monolit" {
                attractions = component "Attractions" "Katalog atrakcji" ""
                reviews = component "Reviews" "Recenzowanie planów podróży" ""
                recommendations = component "Recommendations" "Rekomendowanie planów podróży" ""
                planning = component "Planning" "Planowanie podróży" ""
                complaints = component "Complaints" "Reklamacje i spory" ""
                profiles = component "Profiles" "Profile użytkowników" ""
                booking = component "Bookings" "Rezerwowanie miejsc w obiektach" ""
                payments = component "Payments" "Obsługa płatności" ""
            }
            clientPortal = container "Portal dla podróżujących" "" "HTML/CSS" "Web Browser" {
                traveler -> this "Planuje podróż"
                supervisor -> this "Przypisuje agenta do planowania podróży"
                travelAgent -> this "Przygotowuje wskazówki do planów"
                hotelOwner -> this "Wynajmuje pokoje"
            }
            bokPortal = container "SPA dla biura obsługi klienta" "" "Javascript/Frameworks" "Web Browser" {
                BOK -> this "Rozwiązuje problemy klienta"
            }
            backendDatabase = container "Baza danych" "Relacyjna baza danych" "" "DB" {
                backend_tripMaker -> this "Czyta i zapisuje dane" "H2/PostgreSQL"
            }
        }

        # external systems
        payment_system = softwareSystem "PayU" "Bramka płatności, na przykład PayU" "External System" {

        }

        # relationships
        tripMaker -> payment_system "Realizowanie płatności" ""
        clientPortal -> backend_tripMaker "Wywołuje API" "HTTP/JSON"

        bokPortal -> backend_tripMaker "Wywołuje API" "HTTP/JSON"
        # bokPortal -> complaints "Rozwiązuje spory" ""

        BOK -> complaints "Rozwiązuje spory" ""

        traveler -> attractions "Przegląda atrakcje" ""
        traveler -> reviews "Recenzuje plany podróży" ""
        traveler -> planning "Planuje podróż" ""
        traveler -> profiles "Zarządza swoim profilem" ""
        traveler -> booking "Rezerwuje miejsca pobytu" ""
        traveler -> complaints "Składa reklamacje" ""

        travelAgent -> planning "Przygotowuje plan podróży" ""
        travelAgent -> attractions "Wybiera atrakcje do zaproponowania" ""

        hotelOwner -> booking "Wynajmuje swój obiekt" ""

        supervisor -> planning "Przydziela agenta podróży" ""
        supervisor -> attractions "Dodaje atrakcje do katalogu" ""
        supervisor -> reviews "Akceptuje recenzje" ""

        planning -> recommendations "Pobiera rekomendacje" "SYNC"
        planning -> booking "Pobiera zarezrwowane pobyty" "ASYNC"
        planning -> reviews "Informuje o zrealizowanym planie" "ASYNC"
        planning -> profiles "Informuje o zrealizowanym planie" "ASYNC"
        planning -> attractions "Używa" "SYNC"

        booking -> payments "Zleca dokonanie płatności" "SYNC"

        payments -> payment_system "Używa" "SYNC"
    }

    views {
        systemContext tripMaker {
            include *
        }

        container tripMaker {
            include *
        }

        component backend_tripMaker "Components" {
            include *
            # autoLayout
        }

        styles {
            element "External System" {
                background #999999
                # color #999999
            }
            element "Web Browser" {
                shape WebBrowser
            }
            element "DB" {
                shape Cylinder
            }
        }

        theme default
    }

    configuration {
        scope softwaresystem
    }
}