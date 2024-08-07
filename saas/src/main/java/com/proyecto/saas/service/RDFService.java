package com.proyecto.saas.service;

import java.io.ByteArrayOutputStream;
import java.io.StringWriter;
import java.util.List;

import org.apache.jena.query.Query;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.QueryFactory;
import org.apache.jena.query.ResultSet;
import org.apache.jena.query.ResultSetFormatter;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.ResourceFactory;
import org.apache.jena.vocabulary.RDF;
import org.springframework.stereotype.Service;

import com.proyecto.saas.dto.CourseDTO;
import com.proyecto.saas.dto.SubscriptionDTO;
import com.proyecto.saas.dto.UserDTO;



@Service
public class RDFService {

    private final UserService userService;
    private final CourseService courseService;
    private final SubscriptionService subscriptionService;

    public RDFService(UserService userService, CourseService courseService, SubscriptionService subscriptionService) {
        this.userService = userService;
        this.courseService = courseService;
        this.subscriptionService = subscriptionService;
    }

    public String createRDFForUsers() {
        Model model = ModelFactory.createDefaultModel();
        String ns = "http://www.example.com/ontology#";

        List<UserDTO> users = userService.getAllUsers();
        for (UserDTO user : users) {
            Resource userResource = model.createResource(ns + "user/" + user.getId())
                    .addProperty(RDF.type, "User")
                    .addProperty(ResourceFactory.createProperty(ns + "firstName"), user.getFirstName())
                    .addProperty(ResourceFactory.createProperty(ns + "lastName"), user.getLastName())
                    .addProperty(ResourceFactory.createProperty(ns + "email"), user.getEmail())
                    .addProperty(ResourceFactory.createProperty(ns + "userType"), user.getUserType());
        }

        StringWriter out = new StringWriter();
        model.write(out, "RDF/XML");
        return out.toString();
    }

    public String createRDFForCourses() {
        Model model = ModelFactory.createDefaultModel();
        String ns = "http://www.example.com/ontology#";

        List<CourseDTO> courses = courseService.getAllCourses();
        for (CourseDTO course : courses) {
            Resource courseResource = model.createResource(ns + "course/" + course.getId())
                    .addProperty(RDF.type, "Course")
                    .addProperty(ResourceFactory.createProperty(ns + "title"), course.getTitle())
                    .addProperty(ResourceFactory.createProperty(ns + "description"), course.getDescription())
                    .addProperty(ResourceFactory.createProperty(ns + "status"), course.getStatus())
                    .addProperty(ResourceFactory.createProperty(ns + "creatorId"), String.valueOf(course.getCreatorId()));
        }

        StringWriter out = new StringWriter();
        model.write(out, "RDF/XML");
        return out.toString();
    }

    public String createRDFForSubscriptions() {
        Model model = ModelFactory.createDefaultModel();
        String ns = "http://www.example.com/ontology#";

        List<SubscriptionDTO> subscriptions = subscriptionService.getAllSubscriptions();
        for (SubscriptionDTO subscription : subscriptions) {
            Resource subscriptionResource = model.createResource(ns + "subscription/" + subscription.getId())
                    .addProperty(RDF.type, "Subscription")
                    .addProperty(ResourceFactory.createProperty(ns + "consumerId"), String.valueOf(subscription.getConsumerId()))
                    .addProperty(ResourceFactory.createProperty(ns + "courseId"), String.valueOf(subscription.getCourseId()))
                    .addProperty(ResourceFactory.createProperty(ns + "subscriptionDate"), subscription.getSubscriptionDate().toString());
        }

        StringWriter out = new StringWriter();
        model.write(out, "RDF/XML");
        return out.toString();
    }

    public String createCombinedRDF() {
        Model model = ModelFactory.createDefaultModel();
        String ns = "http://www.example.com/ontology#";

        // Agregar usuarios
        List<UserDTO> users = userService.getAllUsers();
        for (UserDTO user : users) {
            Resource userResource = model.createResource(ns + "user/" + user.getId())
                    .addProperty(RDF.type, "User")
                    .addProperty(ResourceFactory.createProperty(ns + "firstName"), user.getFirstName())
                    .addProperty(ResourceFactory.createProperty(ns + "lastName"), user.getLastName())
                    .addProperty(ResourceFactory.createProperty(ns + "email"), user.getEmail())
                    .addProperty(ResourceFactory.createProperty(ns + "userType"), user.getUserType());
        }

        // Agregar cursos
        List<CourseDTO> courses = courseService.getAllCourses();
        for (CourseDTO course : courses) {
            Resource courseResource = model.createResource(ns + "course/" + course.getId())
                    .addProperty(RDF.type, "Course")
                    .addProperty(ResourceFactory.createProperty(ns + "title"), course.getTitle())
                    .addProperty(ResourceFactory.createProperty(ns + "description"), course.getDescription())
                    .addProperty(ResourceFactory.createProperty(ns + "status"), course.getStatus())
                    .addProperty(ResourceFactory.createProperty(ns + "creatorId"), String.valueOf(course.getCreatorId()));
        }

        // Agregar suscripciones
        List<SubscriptionDTO> subscriptions = subscriptionService.getAllSubscriptions();
        for (SubscriptionDTO subscription : subscriptions) {
            Resource subscriptionResource = model.createResource(ns + "subscription/" + subscription.getId())
                    .addProperty(RDF.type, "Subscription")
                    .addProperty(ResourceFactory.createProperty(ns + "consumerId"), String.valueOf(subscription.getConsumerId()))
                    .addProperty(ResourceFactory.createProperty(ns + "courseId"), String.valueOf(subscription.getCourseId()))
                    .addProperty(ResourceFactory.createProperty(ns + "subscriptionDate"), subscription.getSubscriptionDate().toString());
        }

        StringWriter out = new StringWriter();
        model.write(out, "RDF/XML");
        return out.toString();
    }

    public String queryRDF(String queryString) {
        Model model = ModelFactory.createDefaultModel();
        String ns = "http://www.example.com/ontology#";

        // Add users
        List<UserDTO> users = userService.getAllUsers();
        for (UserDTO user : users) {
            Resource userResource = model.createResource(ns + "user/" + user.getId())
                    .addProperty(RDF.type, "User")
                    .addProperty(ResourceFactory.createProperty(ns + "firstName"), user.getFirstName())
                    .addProperty(ResourceFactory.createProperty(ns + "lastName"), user.getLastName())
                    .addProperty(ResourceFactory.createProperty(ns + "email"), user.getEmail())
                    .addProperty(ResourceFactory.createProperty(ns + "userType"), user.getUserType());
        }

        // Add courses
        List<CourseDTO> courses = courseService.getAllCourses();
        for (CourseDTO course : courses) {
            Resource courseResource = model.createResource(ns + "course/" + course.getId())
                    .addProperty(RDF.type, "Course")
                    .addProperty(ResourceFactory.createProperty(ns + "title"), course.getTitle())
                    .addProperty(ResourceFactory.createProperty(ns + "description"), course.getDescription())
                    .addProperty(ResourceFactory.createProperty(ns + "status"), course.getStatus())
                    .addProperty(ResourceFactory.createProperty(ns + "creatorId"), String.valueOf(course.getCreatorId()));
        }

        // Add subscriptions
        List<SubscriptionDTO> subscriptions = subscriptionService.getAllSubscriptions();
        for (SubscriptionDTO subscription : subscriptions) {
            Resource subscriptionResource = model.createResource(ns + "subscription/" + subscription.getId())
                    .addProperty(RDF.type, "Subscription")
                    .addProperty(ResourceFactory.createProperty(ns + "consumerId"), String.valueOf(subscription.getConsumerId()))
                    .addProperty(ResourceFactory.createProperty(ns + "courseId"), String.valueOf(subscription.getCourseId()))
                    .addProperty(ResourceFactory.createProperty(ns + "subscriptionDate"), subscription.getSubscriptionDate().toString());
        }

        Query query = QueryFactory.create(queryString);
        try (QueryExecution qexec = QueryExecutionFactory.create(query, model)) {
            ResultSet results = qexec.execSelect();
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            ResultSetFormatter.outputAsJSON(outputStream, results);
            return outputStream.toString();
        }
    }
}
