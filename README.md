🐇 Configurando e executando o RabbitMQ (Windows)

1️⃣ Instalação
- Baixe e instale o Erlang e o RabbitMQ (versões compatíveis).
- Reinicie o PC se necessário.

2️⃣ Iniciando o serviço
net start RabbitMQ
Para parar:
net stop RabbitMQ

3️⃣ Habilitar o painel de gerenciamento
rabbitmq-plugins enable rabbitmq_management
net stop RabbitMQ
net start RabbitMQ

4️⃣ Acessar o painel
URL: http://localhost:15672
Usuário "padrão": guest
Senha "padrão": guest

5️⃣ Verificar status
rabbitmqctl status
