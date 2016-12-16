::-----------------------------------------------------------------------------
:: Jose Junior
:: 10.2013
::-----------------------------------------------------------------------------
@echo off

SetLocal

title Billing Test Integrator.

:: Color attributes are specified by TWO hex digits.
:: The first corresponds to the background; the second the foreground.  
:: Each digit can be any of the following values:
:: 
::     0 = Black       8 = Gray
::     1 = Blue        9 = Light Blue
::     2 = Green       A = Light Green
::     3 = Aqua        B = Light Aqua
::     4 = Red         C = Light Red
::     5 = Purple      D = Light Purple
::     6 = Yellow      E = Light Yellow
::     7 = White       F = Bright White
color 0B

echo.
echo ****************************************************************
echo **            Billing Test Integrator Starter V1.0            **
echo **                                     Jose Junior            **
echo **                                         10.2013            **
echo ****************************************************************
echo.

:: Versão Java que precisamos para correr o Billing Test Integrator.
set JAVA_VERSION=1.7
echo Versao Java que vamos usar: Java 1.7


::-----------------------------------------------------------------------------
:: Verificar se a versão Java na Path do Windows é a que precisamos.
::-----------------------------------------------------------------------------
:: Pegar a versão do Java do ambiente (system path).
for /f "tokens=3" %%t in ('java -version 2^>^&1 ^| findstr /i "version"') do (
	set SYSTEM_JAVA_VERSION=%%t
)

:: Remover as aspas da versão.
set SYSTEM_JAVA_VERSION=%SYSTEM_JAVA_VERSION:"=%
echo Versao Java do ambiente:    Java %SYSTEM_JAVA_VERSION%

:: Remover o build da versão.
for /f "delims=. tokens=1-2" %%a in ("%SYSTEM_JAVA_VERSION%") do (
    set SYSTEM_JAVA_VERSION=%%a.%%b
)

:: Comparar versão do sistema com a que precisamos.
if %SYSTEM_JAVA_VERSION% == %JAVA_VERSION% (
	echo Versoes compativeis!
	
	:: Buscar a path do Java.
	for /f %%t in ("java.exe") do (
		set JavaPath=%%~dp$PATH:tjavaw
	)
	
	:: Aplicar o patch.
	goto printHome
)

echo Versoes nao compativeis!    Procurando outras versoes do Java... 


::-----------------------------------------------------------------------------
:: Verificar se existe uma instalação da versão que precisamos no sistema.
::-----------------------------------------------------------------------------
"%JAVA_HOME%"\bin\java -version:1.7 -version > nul 2>&1
if %ERRORLEVEL% == 0 goto found


::-----------------------------------------------------------------------------
:: Não foi encontrada a versão 7. 
::-----------------------------------------------------------------------------
echo Nao foi encontrada a versao 7 do Java no seu sistema. 
echo Por favor, instale o Java 7 e tente novamente. 
goto end


::-----------------------------------------------------------------------------
:: Versão 7 encontrada.
::-----------------------------------------------------------------------------
:found
echo Versao 7 esta' instalada!   Procurando pelo diretorio da instalacao... 

:: Procurar por JDK compatível.
FOR /F "skip=2 tokens=2*" %%a IN ('REG QUERY "HKLM\Software\JavaSoft\Java Development Kit\1.7" /v JavaHome 2^>nul') DO ( 
	set JavaPath=%%b\bin\javaw
)
if "[%JavaPath%]" == "[]" (
	:: Procurar por JRE compatível.
	FOR /F "skip=2 tokens=2*" %%a IN ('REG QUERY "HKLM\Software\JavaSoft\Java Runtime Environment\1.7" /v JavaHome 2^>nul') DO (
		set JavaPath=%%b\bin\javaw
	)
	:: Caso a path seja vazia, quer dizer que não existem versões compatíveis instaladas no sistema.
	if "[%JavaPath%]" == "[]" (
		echo Versao nao encontrada!
		goto end
	)
)

:printHome
echo Versao compativel em:       %JavaPath%
echo.
echo Corrigindo o diretorio atual para o caso do modo administrador.
@cd /d "%~dp0"
echo Diretorio corrente alterado para: %~dp0


::-----------------------------------------------------------------------------
:: Verificar se o patch necessário para executar o BTI foi apicado.
:: Ver o HowTo, seção "Erros e soluções de problemas". 
::-----------------------------------------------------------------------------
:patch
echo.
echo|set /p=Verificando patch... 

:: Condicao AND.
if exist "%JavaPath%\..\..\lib\endorsed" (
  if exist "%JavaPath%\..\..\lib\endorsed\javax.annotation_1.0.0.0_1-0.jar" (
	if exist "%JavaPath%\..\..\lib\endorsed\javax.xml.bind_2.1.1.jar" (
	  if exist "%JavaPath%\..\..\lib\endorsed\javax.xml.ws_2.1.1.jar" (
	    echo Patch ja' aplicado!
		goto run
	  )
	)
  )
)

echo Patch ainda nao aplicado! 
echo Aplicando patch... 
set source="%~dp0..\doc\endorsed"
set destination="%JavaPath%\..\..\lib\endorsed"
echo Fonte:   %source%
echo Destino: %destination%
xcopy /s/y/i/o/q %source% %destination%

if %ERRORLEVEL% == 0 (
  echo Patch aplicado com sucesso!
  goto run
)

echo Erro na aplicacao do patch! 
echo Se tiver erros na execucao, por favor, experimente executar o script 
echo em modo administrador, ou copie o patch manualmente, conforme a documentacao. 


::-----------------------------------------------------------------------------
:: Executar a aplicação.
::-----------------------------------------------------------------------------
:run
:: Ir para diretoria raiz do projeto para termos os logs nos lugares certos.
cd ..
echo. 
echo Executando aplicacao... 
echo Chamando: start %JavaPath% -jar %~dp0..\build\BillingTestIntegrator.jar
echo. 
echo. 
start "%JavaPath% -jar" %~dp0..\build\BillingTestIntegrator.jar


::-----------------------------------------------------------------------------
:: Fim.
::-----------------------------------------------------------------------------
:end
echo Terminado com sucesso.

EndLocal

::exit



