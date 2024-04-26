# :money_with_wings: Conversor de Moedas :money_with_wings:

Um programa simples para conversão de valores em várias moedas. 

## :desktop_computer: Utilização :desktop_computer:

Basta importar o projeto para o IntelliJ, esperar ele baixar as dependências e executar a classe `ConversorDeMoedas`.

## :dollar: Funcionamento :dollar:

Primeiro, o programa carrega as taxas de conversão utilizando o [frankfurter.app](https://www.frankfurter.app/), uma API de taxa de conversão gratuita e sem criação de conta.

Após isso ele irá perguntar qual o valor deseja converter, em seguida qual a moeda desse valor e por último, para qual moeda deseja converter.

Você pode converter vários valores em seguida e, quando não quiser mais, pode digitar "sair" quando for solicitado o valor.

## :gear: Lógica de Negócio :gear:

Da API é pego a taxa de conversão das moedas em relação ao real(BRL), mas podemos converter de qualquer moeda para qualquer outra, então é necessário realizar a seguinte conta:

$\text{TaxaDeConversãoDoValorParaAlvo} = \frac{\text{TaxaDeConversãoAlvo}}{\text{TaxaDeConversãoDoValor}}$

Por exemplo, se queremos converter 10 USD para CAD:

$TaxaDeConversãoCAD = 0.26503$

$TaxaDeConversãoUSD = 0.19407$

$\text{TaxaDeConversãoUSDParaCAD} = \frac{\text{TaxaDeConversãoCAD}}{\text{TaxaDeConversãoUSD}} = \frac{0.26503}{0.19407} = 1.36564$

$\text{ValorEmCAD} = \text{TaxaDeConversãoUSDParaCAD} \cdot \text{Valor} = 1.36564 \cdot 10 = 13.6564$

Então 10 USD é igual à 13,64 CAD