import {Component, OnInit} from '@angular/core';

@Component({
  selector: 'app-privacy',
  template: `
    <h1>Polityka prywatności</h1>
    <h3>Autorzy aplikacji Brewed szanują prawo użytkowników aplikacji do prywatności i oświadcza, że dokłada wszelkich
      starań, aby nie gromadzić żadnych danych oprócz tych, które są konieczne do prawidłowego funkcjonowania
      aplikacji.</h3>
    <ol>
      <li>Wszelkie dane, które mogłyby pozwolić na identyfikacje (ustalenie tożsamości) Użytkownika, nie są gromadzone
        przez aplikację ani udostępniane przez Usługodawcę jakimkolwiek osobom trzecim.
      </li>
      <li>Podczas użytkowania aplikacji zapisywane są tylko tymczasowe dane na urządzeniu klienta.
      </li>
      <li>Aplikacja anonimowo zbiera dane statystyczne udostępniane przez system. Za zbieranie tych danych oraz sposobu
        użytkowania aplikacji odpowiedzialna jest usługa Google Analytics, której właścicielem jest firma Google inc.
        Polityka bezpieczeństwa usługi Google Analytics.
      </li>
      <li>Aplikacja może korzystać z następujących uprawnień:
        <ul>
          <li>Pamięć - karta SD – aplikacja może zapisywać dane w pamięci urządzenia dzięki czemu użycie kilku
            funkcjonalności dostępne jest w trybie offline.
          </li>
          <li>Pełny dostęp do sieci – w większości przypadków wymagany do aktualizacji danych oraz wyświetlania części
            danych podczas przeglądania, w trybie online. Dodatkowo w tym trybie działają usługi Google Analytics, które
            umożliwiają nam anonimowe monitorowanie zachowań użytkownika.
          </li>
          <li>Dostęp do aparatu – uprawnienie potrzebne do funkcjonalności, które wymagają zrobienie zdjęcia, czyli
            rozpoznanie piwa po zdjęciu jego etykiety.
          </li>
          <li>Aplikacja zbiera anonimowo identyfikatory urządzeń użytkowników przy ocenianiu piw. Dane są przetwarzane
            na serwerze, ale nie gromadzimy żadnej danej pozwalającej zidentyfikować nam danego użytkowika, a jedynie
            jego urządzenie.
          </li>
        </ul>
      </li>
    </ol>
    <br>
    <h3>
      W razie jakichkolwiek pytań lub niejasności chętnie służymy pomocą. Skontaktuj się z nami na adres
      kontakt.brewed@gmail.com</h3>
  `,
  styles: []
})
export class PrivacyComponent implements OnInit {

  constructor() {
  }

  ngOnInit(): void {
  }

}
