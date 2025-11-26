Git config:
git add .
git config user.name "Naveen-Sankhala"
git config user.email "naveen.sankhala24@gmail.com
git commit -m "Auth-service changes"


As prectice to create a centralize banking micro service app.

🚀 Startup Order (with dependencies)

   [1] Service Registry (Eureka Server)
        │
        ▼
   [2] Config Server  ────────────────┐
        │                             │
        ▼                             │
   [3] API Gateway (pulls config)     │
        │   │                         │
        │   └─> registers to Eureka   │
        │                             │
        ▼                             │
   [4] Business Services (Account Service, Bank Config Service, etc.)
        │   └─> fetch config from Config Server
        │   └─> register with Eureka
        │
        ▼
   ✅ Fully wired system

✅ Final Startup Order:

	1. First Eureka Server (Service Registry is up)
	2. Then Config Server (so configs can be pulled).
	3. Then API Gateway (so it can route).
	4. Finally Business Services (Bank Config Service, Account Service, etc.)



📂 Config Repo Structure (Git Repo used by Config Server)
config-repo/
│
├── application.yml              # common defaults for all services
├── application-dev.yml          # common DEV environment config
├── application-prod.yml         # common PROD environment config
│
├── account-service.yml          # service-specific defaults
├── account-service-dev.yml      # service-specific DEV config
├── account-service-prod.yml     # service-specific PROD config
│
├── bank-config-service.yml
├── bank-config-service-dev.yml
├── bank-config-service-prod.yml
│
└── api-gateway.yml
    api-gateway-dev.yml
    api-gateway-prod.yml

=>Rule:

application*.yml → shared by all services.

{service-name}*.yml → only loaded when a service has spring.application.name={service-name}