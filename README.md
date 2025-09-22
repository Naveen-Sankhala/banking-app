As prectice to create a centralize banking micro service app.
          
		  
		  +----------------+
          |     Client     |
          +--------+-------+
                   |
                   v
          +----------------+
          |  API Gateway   |  <-- Routing, load balancing, security, rate limiting
          +--------+-------+
                   |
        ---------------------------
        |                         |
        v                         v
+----------------+        +--------------------+
| Config Server  |        | Bank Config Service|
+----------------+        +--------------------+
