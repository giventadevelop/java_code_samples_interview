package com.boot2;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class JunitTestMockAdadpterPort {

/*

    CC W
    O results
1
        package com.wellsfargo.ccfv.declinedtransactions.vi.integration.adapter.rest;
2
        3 3 import com.google.gson.Gson;
4 import com.google.gson.GsonBuilder;
5
        import com.wellsfargo.ccfv.declinedtransactions.common.api.Constants;
6
        import com.wellsfargo.ccfv.declinedtransactions.common.api.ListDebitAccountTransactionDetailsRequest;
7
        import com.wellsfargo.ccfv.declinedtransactions.common.api.LocalDateAdapter;
8
        import com.wellsfargo.ccfv.declinedtransactio .declinedtransactions.common.exception.ApplicationException;
9
        import com.wellsfargo.ccfv.declinedtransactions.common.exception. DataNotFoundException;
10
        import com.wellsfargo.ccfv.declinedtransactions.common.exception.ValidationException;
11
        import com.wellsfargo.ccfv.declinedtransactions.common.util.Utilities;
12
        import com.wellsfargo.ccfv.declinedtransactions.v1.business.usecase.port.input.GetDeclinedTransactionsRequestDTO;
13
        import com.wellsfargo.ccfv.declinedtransactions.v1.business.usecase.port.output.GetDeclinedTransactionsResponseDTO;
14
        15
        import com.wellsfargo.ccfv.declinedtransactions.v1.integration.adapter.GetDeclinedTransactionsAdapterPort;
import com.wellsfargo.ccfv.declinedtransactions.common.api.ListDebitAccountTransactionDetailsResponse;
16
        import com.wellsfargo.ccfv.webclient.GenericWebClient;
17
        import com.wellsfargo.ccfv.webclient.WebClientResponseException;
18 import jakarta.validation.Valid;
19 import lombok.Data;
20 import lombok.extern.slf4j.Slf4j;
21
        import org.springframework.beans.factory.annotation.Autowired;
22
        import org.springframework.beans.factory.annotation.Qualifier;
23
        import org.springframework.beans.factory.annotation.Value;
24
        import org.springframework.core.env.Environment;
25
        import org.springframework.stereotype.Service;
26
        27 import java.time.LocalDate;
28
        import java.time.ZonedDateTime;
29
        import java.time.format.DateTimeFormatter;
30 import java.util.Arrays;
31 import java.util.HashMap;
32 import java.util.Map;
33
        import java.util.Optional;
34
        35
    @Slf4j 9 usages U788410
36
    @Data
    I
    cfv-declined-transactions > src > main > java > com > wellsfargo > ccfv > declinedtransactions > v1 > integration > adapter > rest >
    GetDeclim
79
        return Optional.of(getDeclin

    Q-
            7
            8
            9
            2
    Cc W
    results
    @Service("getDeclined TransactionsAdapterPortImpl")
    public class GetDeclinedTransactionsAdapterPortImpl implements GetDeclinedTransactionsAdapterPort {
        @Autowired
        private Environment environment;
        @Value("${ext-services.getDcbsDeclinedTransactionsApi}")
        private String dcbsLdactdUrl;
        private ListDebitAccountTransactionDetailsResponse listDebitAccountTransactionDetailsResponse;
        private final GenericWebClient genericWebClient;
        private final GetDeclinedTransactions ResponseDTO getDeclined TransactionsResponseDTO;
        public GetDeclinedTransactionsAdapterPortImpl(@Qualifier("secureGenericWebClient") GenericWebClient genericWebClient, final GetDeclinedTransactionsResponseDTO getDec
    }
this.genericWebClient = genericWebClient;
this.getDeclinedTransactionsResponseDTO = getDeclinedTransactionsResponseDTO;
    @Override U788410
    public Optional<GetDeclinedTransactionsResponseDTO> execute(@Valid GetDeclinedTransactionsRequestDTO getDeclinedTransactionsRequestDTO) {
        log.debug("Executing adapter port to get declined transactions with request: {}", getDeclinedTransactionsRequestDTO);
        try {
            log.info("Entering GetDeclined TransactionsAdapterPortImpl.execute()");
            ListDebitAccountTransactionDetailsRequest listDebitAccountTransactionDetailsRequest = getDeclined TransactionsRequestDTO.getListDebitAccountTransactionDetails
// Create a Gson instance with the custom LocalDateAdapter
            Gson gson = new GsonBuilder()
                    .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
                    .create();
            String json = gson.toJson(listDebitAccountTransactionDetailsRequest);
            log.debug("Converted request to JSON: {}", json);
            Map<String, String> declinedTransactionsHeaders = buildHeaders(getDeclinedTransactionsRequestDTO.getSenderAppId(), getDeclinedTransactionsRequestDTO.getSum
                    log.debug("Built headers for request: {}", declined TransactionsHeaders);
            log.info("invoking dcbs api url: {} for the environment profile {}", dcbsLdactdUrl, Arrays.stream(environment.getActiveProfiles()).findFirst().get());
            Arrays.stream(environment.getActiveProfiles()).forEach(String profile -> log.info("Active profile: {}", profile));
            listDebitAccountTransactionDetailsResponse = genericWebClient.post(dcbsLdactdUrl, listDebitAccountTransactionDetailsRequest, ListDebitAccountTransaction
                            > adapter > rest > GetDeclined TransactionsAdapterPortImpl >
                            execute
                    79
                    oclined-transactions > src>main > java > com > wellsfargo > ccfv > declinedtransactions > v1 > integration
            return Optional.of (getDeclined TransactionsResponseDTO);

            Q-
                    Cc W*
                    O results
↑↓
            TransactionsResponseDTO.java
            Comparison Fallure
            Assertionalledmor.class
            public class GetDeclinedTransactionsAdapterPortImpl implements GetDeclinedTransactionsAdapterPort {
                public Optional<GetDeclinedTransactionsResponseDTO execute(@Valid GetDeclinedTransactionsRequestDTO getDeclinedTransactionsRequestDTO) {
                    log.debug("Built headers for request: ()", declinedTransactionsHeaders);
                    log.info("invoking debs api url: {} for the environment profile {}", dcbsLdactdUrl, Arrays.stream(environment.getActiveProfiles()).findFirst().get());
                    Arrays.stream(environment.getActiveProfiles()).forEach(String profile -> log.info("Active profile: (}", profile));
                    ListDebitAccountTransactionDetailsResponse genericWebClient.post(dcbsLdactdUrl, listDebitAccountTransactionDetailsRequest, ListDebitAccountTransactionDetailsResponse.class, declinedTransactionsHeaders);
                    log.info("Received response from genericWebClient.post");
                    getDeclinedTransactionsResponseDTO.setListDebitAccountTransactionDetailsResponse (listDebitAccountTransactionDetailsResponse);
                    Log.debug("Set response DTO with received data");
                    log.info("Returning response for declined transactions: {}", getDeclinedTransactionsResponseDTO);
                    return Optional.of(getDeclined TransactionsResponseDTO);
                } catch (Exception ex) {
                    Log.error("Exception occurred while executing adapter port to get declined transactions", ex);
                    if (ex instanceof WebClientResponseException) {
                    }
                    WebClientResponseException webClientResponseException = (WebClientResponseException) ex;
                    if (webClientResponseException.getStatusCode() == 422 && webClientResponseException.getMessage().contains("No record found*)) {
                            ListDebitAccountTransactionDetailsResponse new ListDebitAccountTransactionDetailsResponse();
                    getDeclinedTransactions ResponseDTO.setListDebitAccountTransactionDetailsResponse (listDebitAccountTransactionDetailsResponse);
                    Log.debug("Set response DTO with received data");
                    log.info("Returning null response for declined transactions due to 422 status code ");
                    return Optional.of(getDeclinedTransactionsResponseDTO);
                } if (
                        webClientResponseException.getStatusCode() == 422 && webClientResponseException.getMessage().contains("debitAccountNbr/cardNumber is invalid or missing in the request")) {
                    ListDebitAccountTransactionDetailsResponse = new ListDebitAccountTransactionDetailsResponse();
                    getDeclinedTransactionsResponseDTO.setListDebitAccountTransactionDetails Response (listDebitAccountTransactionDetailsResponse);
                    log.info("debitAccountNbr/cardNumber is invalid or missing in the request");
                    throw new ValidationException("debitAccountNbr/cardNumber is invalid or missing in the request.400_BAD_REQUEST");
                    if (webClientResponseException.getStatusCode() == 404 && webClientResponseException.getMessage().contains("No record found")) {
                        listDebitAccountTransactionDetailsResponse = new ListDebitAccountTransactionDetailsResponse();
                        getDeclinedTransactionsResponseDTO.setListDebitAccountTransactionDetailsResponse (listDebitAccountTransactionDetailsResponse);
                        Cond Found or inactive card in DCBS");
                        adapter rest GetDeclined Transactions Adapter Portimpl > execute

                                Q
                        38
                        55
                        97
                        98
                        99
                        Cc W
↑↓ 0 results
                        public class GetDeclinedTransactionsAdapterPortImpl implements GetDeclinedTransactionsAdapterPort {
                            public Optional GetDeclinedTransactionsResponseDTos execute(@Valid GetDeclinedTransactionsRequestDTO getDeclinedTransactionsRequestDTO) {
                            }
throw new ValidationException("debitAccountNbr/cardNumber is invalid or missing in the request.400_BAD_REQUEST");
if (webClientResponseException.getStatusCode() == 404 && webClientResponseException.getMessage().contains("No record found")) {
                                listDebitAccountTransactionDetailsResponse = new ListDebitAccountTransactionDetailsResponse():
                                getDeclinedTransactionsResponseDTO.setListDebitAccountTransactionDetailsResponse(listDebitAccountTransactionDetailsResponse);
                                log.info("No Card Found or inactive card in DCBS");
                                throw new DataNotFoundException("No Card Found or inactive card in DCBS with 484 status code");
                                throw ex;
                                07
                            }
08
        09
        10
        11
        12
        13
        14
        15
        16
        17
        18
        19
        20
        21
        22
        23
                        }
                        24
                        25
                        26
                    }
                    27
                    28
                    public Map<String, String> buildHeaders (String originatingAppId, String subAppId, String trackingId, String headerType) { 2 usages & U788410
                        log.debug("Building headers with originatingAppId={}, subAppId={}, trackingId={}, headerType={}", originatingAppId, subAppId, trackingId, headerType);
                        Map<String, String> headers = new HashMap<>();
                        headers.put("Content-Type", "application/json");
                        if (Constants.SOA.equalsIgnoreCase(headerType)) {
                            01
                            02
                            03
                            04
                        }
                        05
                    }
                    06
                    headers.put("WF-senderApplicationId", Constants.CCFV_APPLICATION_ID);
                    headers.put("WF-senderSubApplicationId", subAppId);
                    headers.put("WF-originatingApplicationId", originatingAppId);
                    headers.put("WF-senderHostName", Utilities.getHostName());
                    headers.put("WF-creationTimeStamp", ZonedDateTime.now().format(DateTimeFormatter.ISO_OFFSET_DATE_TIME));
                    headers.put("WF-senderMessageId", trackingId);
                    log.debug("Built SOA headers: {}", headers);
                    log.debug("headers passed to DCBS genericWebClient: {}", headers);
                    return headers;
                    wione 1 integration > adapter > rest > GetDeclined Transactions Adapter PortImpl >
                            execute
                    theclined TransactionsResponseDTO);

*/


}
