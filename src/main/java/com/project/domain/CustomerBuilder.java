package com.project.domain;

import org.json.JSONObject;

public class CustomerBuilder {

        public static Customer buildIndividualFromJson(String json_string) {

                try {

                        // parsing file "JSONExample.json"
                        JSONObject jsonObject = new org.json.JSONObject(json_string);

                        // Extracting fields from the JSON object
                        int identifier = (int) jsonObject.get("identifier");
                        String fullName = (String) jsonObject.get("fullName");
                        String emailAddress = (String) jsonObject.get("emailAddress");
                        String secretKey = (String) jsonObject.get("secretKey");

                        // Create an Individual object
                        Customer customer = new Customer();
                        customer.setId(identifier);
                        customer.setName(fullName);
                        customer.setEmail(emailAddress);
                        customer.setPassword(secretKey);

                        return customer;

                } catch (Exception e) {
                        return null;
                }
        }

        public static String convertCustomerToJson(Customer customer) {
                JSONObject jo = new JSONObject();

                // putting data to JSONObject
                jo.put("name", customer.getName());
                jo.put("email", customer.getEmail());
                jo.put("password", customer.getPassword());
                jo.put("id", customer.getId());

                String out = jo.toString();
                return out;
        }

}
