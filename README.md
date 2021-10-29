# Data-Analyss

## Aplicação

A aplicação faz uma analise dos arquivos com extensão `.dat` que estiverem no repositório de entrada e passa para o repositório de saída um arquivo, referente a cada um, sintetizando as informações do dado.

## Antes de Usar

Vá para seu `%HOMEPATH%` crie um repositório `data`, com um repositório `in` e um repositório `out` dentro.

### Formato dos dados que são lidos
1. *Saleman* = `001çCPFçNameçSalary`
2. *Customer* = `002çCNPJçNameçBusiness Area`
3. *Sale* = `003çSale IDç[Item ID-Item Quantity-Item Price]çSalesman name`


### Retorna 
1. Quantidade de clientes.
2. Quantidade de vendedores.
3. O ID da venda mais cara e o valor que ela foi.
4. Quem foi o pior vendedor (ou vendedores, caso mais de um tenha vendido o mesmo valor) e o valor total que conseguiram vender.


### A Aplicação suporta:
1. Ler arquivos de até `30kb` (Esse valor pode ser ajustado na classe de repositório).
2. Ler arquivos com nomes com `ç`.
3. ler arquivos com dados na mesma linha.
4. Caso um vendedor faça uma venda e não esteja registrado, a apicação avisa que esse vendedor não consta nos .registros, mas não é interrompida.
5. Caso, dentro dos detalhamentos dos produtos vendidos, falte algum dos critérios a aplicação dá um aviso e  continua a calcular o valor da venda ignorando este.
