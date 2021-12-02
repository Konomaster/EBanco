# EBanco

Compilation and Run Instructions

Windows:

javac -cp ..\..\lib\jgroups-3.6.4.Final.jar Ebanco_visao.java

java "-Djava.net.preferIPv4Stack=true" -cp "..\..\lib\jgroups-3.6.4.Final.jar;..\" pacote.Classe_principal_compilada

Linux Ubuntu:

javac -cp ../../lib/jgroups-3.6.4.Final.jar Ebanco_visao.java

java -Djava.net.preferIPv4Stack=true -cp ../:../../lib/jgroups-3.6.4.Final.jar pacote.Classe_principal_compilada

## Caso der erro de classnotfound no netbeans para resetar o classpath (aka toda vez que abrir o rmiregistry no windows):

1) remova src de biblioteca se tiver adicionado

2) no diretorio de EbancoControle

2.1) javac -cp "..\..\lib\jgroups-3.6.4.Final.jar;..\" .\EbancoControle.java

3) rode o EbancoControle no netbeans

4) delete os .class gerados

5) apos isso va em adicionar jar/pasta e adicione a pasta superior src, ai vai dar certo :)
