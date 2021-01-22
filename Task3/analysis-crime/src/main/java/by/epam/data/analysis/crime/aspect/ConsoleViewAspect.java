package by.epam.data.analysis.crime.aspect;

import by.epam.data.analysis.crime.entity.Crime;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

import java.util.Properties;

@Aspect
@Component
@Slf4j
public class ConsoleViewAspect {
    private static final String ARGUMENT_VERBOSE = "verbose";
    private static final String DELIMITER = "########################################";

    private boolean isVerbose;

    @Pointcut("execution(* by.epam.data.analysis.crime.controller.command.ConsoleCommand.*(..))")
    private void consoleCommand() {
    }

    @Pointcut("execution(* by.epam.data.analysis.crime.controller.command.DownloadAndSaveCrimesConsoleCommand.execute(*))")
    private void getVerbose() {
    }

    @Pointcut("execution(* by.epam.data.analysis.crime.service.JsonConverter.jsonToCrime(*))")
    private void logging() {
    }

    @Around("consoleCommand()")
    private Object delimitViewCommand(ProceedingJoinPoint joinPoint) throws Throwable {
        log.info(DELIMITER);
        log.info(DELIMITER);
        log.info(DELIMITER);
        Object returnValue = joinPoint.proceed();
        log.info(DELIMITER);
        log.info(DELIMITER);
        log.info(DELIMITER);
        return returnValue;
    }

    @Around("getVerbose()")
    private Object isVerbose(ProceedingJoinPoint joinPoint) throws Throwable {
        Object[] objects = joinPoint.getArgs();
        Properties properties = (Properties) objects[0];
        if (properties.getProperty(ARGUMENT_VERBOSE) != null) {
            isVerbose = properties.getProperty(ARGUMENT_VERBOSE).equalsIgnoreCase(Boolean.TRUE.toString());
        }
        return joinPoint.proceed();
    }

    @Around("logging()")
    private Object showConverting(ProceedingJoinPoint joinPoint) throws Throwable {
        Crime returnValue;
        if (isVerbose) {
            Object[] objects = joinPoint.getArgs();
            JSONObject jsonObject = (JSONObject) objects[0];
            log.info(jsonObject.toString());
            returnValue = (Crime) joinPoint.proceed();
            log.info(returnValue.toString());
        } else {
            returnValue = (Crime) joinPoint.proceed();
        }
        return returnValue;
    }
}
