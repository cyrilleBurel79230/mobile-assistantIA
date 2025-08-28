com.jarvisassistant
│
├── data
│   ├── model/              # Modèles de données (User, Command, WeatherResponse...)
│   ├── repository/         # Accès aux données (API, Room, etc.)
│   └── service/            # Services externes (API REST, TTS, STT...)
│
├── domain
│   ├── usecase/            # Logique métier (ex: GetWeatherUseCase)
│   └── mapper/             # Mapping entre DTO et modèles
│
├── presentation
│   ├── ui/                 # Activités, fragments, vues
│   ├── viewmodel/          # ViewModels pour chaque écran
│   └── adapter/            # Adapters RecyclerView, etc.
│
├── utils/                  # Fonctions utilitaires
│
└── di/                     # Injection de dépendances (Hilt/Dagger)

Jetpack compose

Hilt : Outil injection dependances (DI) -  Outil d’injection de dépendances (DI) : permet de fournir automatiquement les objets dont tes classes ont besoin (services, ViewModels, etc.)
- 🚀 Optimisé pour Android : fonctionne avec les Activity, Fragment, ViewModel, Service, etc.
- 🧹 Réduit le code répétitif : plus besoin de créer manuellement des constructeurs ou des factories

j AI MODIDIFE LA VARIABLE D ENVIRONNEMENT JAVA HOME EN 1.17

- 🎙️ Reconnaissance vocale via VoiceCommandService
- 🧠 Analyse IA via Mistral (callIA)
- 🧭 Déduction du contexte via extraireContexteAvecIA()
- 💬 Réponse affichée + 🔊 prononcée via TTSService



