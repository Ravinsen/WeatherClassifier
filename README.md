# Weather Classification App

**Link zur App:** https://weather-djl-app.azurewebsites.net/

Diese Web-App erkennt automatisch den aktuellen Wetterzustand auf hochgeladenen Bildern. Mithilfe eines eigens trainierten KI-Modells identifiziert sie acht unterschiedliche Wetterklassen – visuell dargestellt mit passenden Emojis.  
Ob Sonnenaufgang, Nebel, Regenbogen oder Gewitter: Nutzerinnen und Nutzer erhalten sofort eine verständliche Rückmeldung über das erkannte Wetter.

## Warum diese App?

Wetterbilder gehören zu den am häufigsten geteilten visuellen Inhalten. Die automatische Einordnung solcher Bilder eröffnet zahlreiche Anwendungsfelder – etwa für Wetterdienste, Tourismusportale, Bildarchive oder smarte Fotogalerien.

Diese App bietet:

- Automatische Erkennung von 8 Wetterzuständen
- Emoji-basierte Rückmeldung 
- Eine einfache Web-Oberfläche für Bild-Upload und Ergebnisanzeige  
- On-device KI-Inferenz via ONNX-Modell ohne Cloud-KI-Anbieter  

## Unterstützte Wetterklassen

- ☁️ **Cloudy**  
- 🌧️ **Rainy**  
- ❄️ **Snowy**  
- ⚡ **Lightning**  
- 🌈 **Rainbow**  
- 🌅 **Sunrise**  
- ☀️ **Sunny**  
- 🌨️ **Hail**  

## Datenquelle

Die Trainingsdaten stammen aus zwei öffentlich verfügbaren Bilddatensätzen auf Kaggle:

1. [Multiclass Weather Classification Dataset](https://www.kaggle.com/datasets/somesh24/multiclass-images-for-weather-classification)  
2. [Weather Image Dataset by Jehan Bhathena](https://www.kaggle.com/datasets/jehanbhathena/weather-dataset)

Beide wurden kombiniert, bereinigt und in eine einheitliche Ordnerstruktur überführt, um ein robustes Training zu ermöglichen. Insgesamt umfasst das Trainingsset rund **1'000 Bilder**, verteilt auf 8 Klassen.

## Features

- Upload von beliebigen **JPG-/PNG-Wetterbildern**  
- Sofortige Rückmeldung mit **Wetterklasse + Emoji + Confidence Score**  
- Minimalistische Benutzeroberfläche (Drag & Drop / Upload)  
- Mobilgeräte-optimiert  
- Vollständig **containerisiert und über Azure öffentlich verfügbar**

## Technologiestack

- **Modell:** Transfer Learning mit ResNet18, feingetuned auf 8 Wetterklassen  
- **Training:** PyTorch (lokal), Export als ONNX - Siehe hier: https://github.com/Ravinsen/weather_training
- **Backend:** Java Spring Boot mit DJL (Deep Java Library)  
- **Frontend:** HTML + Bootstrap (modernes UI mit Drag & Drop)  
- **Deployment:** Docker + Azure App Service (Linux)  
- **Hosting:** https://weather-djl-app.azurewebsites.net/

## Projektkontext

Diese App wurde im Rahmen des Moduls **Model Deployment & Maintenance (MDM)** umgesetzt.  
Sie demonstriert die nahtlose Kombination aus Deep Learning, Java-Webentwicklung und Cloud-Deployment – von der Datenaufbereitung bis zur öffentlich erreichbaren App.  

Ziel war es, ein vollständiges, eigenständig trainiertes Modell in eine produktionsreife Webanwendung zu überführen – inkl. ONNX-Inferenz, Dockerisierung und Azure Deployment.