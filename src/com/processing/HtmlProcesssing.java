/*
 * Получение HTML формы, её заполнение и отправка
 */
package com.processing;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.*;
import java.io.File;
import java.io.IOException;
import java.security.GeneralSecurityException;

/**
 *
 * @author Butenko_V
 */
public class HtmlProcesssing {

    public static void main(String[] args) throws GeneralSecurityException, IOException {
        
        WebClient webClient = new WebClient(BrowserVersion.getDefault()); // Запускаем наш "браузер" 
        webClient.setCssEnabled(false);         // Отключаем Css для скорости
        webClient.setJavaScriptEnabled(true);   //поддержка JavaScript. 
        webClient.setUseInsecureSSL(true);      //Разрешаем поддержку SSL
        webClient.setRedirectEnabled(true); //позволяет перейти по адресу, который указан в ответе сервера.
        HtmlPage page = webClient.getPage("http://www.cert.ru/ru/abuse.shtml");   //Открывает указанный адрес
        //  page.save(new File ("savedpage"));
       
       //Чтобы заполнить и отправить на сервер форму - для начала надо её получить. Для этого:
       HtmlForm form = page.getFormByName("REPORT");
      
       //Найти и заполнить  поля для ввода данных вызываем функцию
       HtmlInput inputContact    = form.getInputByName("CONTACT");
       HtmlInput inputTimeStart  = form.getInputByName("TIME-START");
       HtmlInput inputTimeRemark = form.getInputByName("TIME-REMARK");
       HtmlTextArea inputLog     = form.getTextAreaByName("LOG");
             
       //Приступаем к заполнению текстовых полей:
       inputContact.setValueAttribute("Victor Butenko");
       inputTimeStart.setValueAttribute("02/17 00:00");
       inputTimeRemark.setValueAttribute("Дополнение какое-то");
       inputLog.setText("Джава не тормозит, Паша хватит троллить людей!! :)");
       
       
       //Извлечение и Заполнение  выпадающих списков:
       HtmlSelect selectGMT      = form.getSelectByName("TZ");
       HtmlSelect selectNTP      = form.getSelectByName("NTP");  
       HtmlOption optionValue1   = selectGMT.getOption(3);
       HtmlOption optionValue2   = selectNTP.getOption(1);
       
       optionValue1.setSelected(true);
       selectNTP.setSelectedAttribute(optionValue1, true);
       selectNTP.setSelectedAttribute(optionValue2, true);
       
       
       //Когда все поля заполнены   - нужно отправить запрос на сервер. 
       //Для этого нужно отыскать нужную кнопку для отправки данных
       HtmlSubmitInput submit = form.getInputByValue("Отправить");
       
       HtmlPage pageAnswer = submit.click(); //Жмем "submit", возвращается страница ответа.
       page.save(new File ("savedpage"));;
       
       webClient.closeAllWindows();
    }
}
