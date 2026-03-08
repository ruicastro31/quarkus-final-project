package ada.caixaverso.resource;

import ada.caixaverso.dto.CourseRequest;
import ada.caixaverso.dto.CourseResponse;
import ada.caixaverso.dto.LessonRequest;
import ada.caixaverso.dto.LessonResponse;
import ada.caixaverso.model.Course;
import ada.caixaverso.model.Lesson;
import ada.caixaverso.repository.CourseRepository;
import ada.caixaverso.repository.LessonRepository;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@Path("/courses")
public class CourseResource {

    @Inject
    CourseRepository courseRepository;
    @Inject
    LessonRepository lessonRepository;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional
    public Response createCourse(@Valid CourseRequest request) {
        Course course = new Course(request.name());

        courseRepository.persist(course);

        CourseResponse payload = new CourseResponse(course.getId(), course.getName(), List.of());

        return Response.created(URI.create("/courses/" + course.getId()))
                .header("Content-Type", "application/json")
                .entity(payload)
                .build();
    }

    @PUT
    @Path("/{id}")
    @Transactional
    public Response updateCourse(@PathParam("id") Long id, @Valid CourseRequest request) {
        Optional<Course> possibleCourse;
        possibleCourse = courseRepository.findByIdOptional(id);

        if (possibleCourse.isPresent()) {
            Course course = possibleCourse.get();
            course.setName(request.name());

            courseRepository.persist(course);

            return Response.ok(new CourseResponse(course.getId(), course.getName(), List.of())).build();
        }

        return Response.status(Response.Status.NOT_FOUND).build();
    }

    @DELETE
    @Path("{id}")
    @Transactional
    public Response deleteCourse(@PathParam("id") Long id) {
        Optional<Course> possibleCourse;
        possibleCourse = courseRepository.findByIdOptional(id);

        if (possibleCourse.isPresent()) {
            Course course = possibleCourse.get();
            courseRepository.deleteById(id);
            return Response.noContent().build();
        }

        return Response.status(Response.Status.NOT_FOUND).build();
    }

    @GET
    public Response getCourse(){
        List<Course> courses = courseRepository.listAll();
        List<CourseResponse> listResponse = courses.stream().map(c -> new CourseResponse(c.getId(), c.getName(), List.of())).toList();

        return Response.ok(listResponse).build();
    }

    @GET
    @Path("{id}")
    public Response getCoursebyId(@PathParam("id") Long id){
        Optional<Course> possibleCourse;
        possibleCourse = courseRepository.findByIdOptional(id);

        if (possibleCourse.isPresent()) {
            Course course = possibleCourse.get();

            return Response.ok(new CourseResponse(course.getId(), course.getName(), List.of())).build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("{id}/lessons")
    @Transactional
    public Response createLesson(@PathParam("id") Long id, @Valid LessonRequest lessonRequest) {
        Optional<Course> possibleCourse;
        possibleCourse = courseRepository.findByIdOptional(id);
        if(possibleCourse.isEmpty()){
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        Course course = possibleCourse.get();
        Lesson lesson = new Lesson(lessonRequest.name());
        lessonRepository.persist(lesson);

        course.addLesson(lesson);

        LessonResponse payload = new LessonResponse(lesson.getId(), lesson.getName());

        return Response.created(URI.create("/courses/" + course.getId() + "/lessons/" + lesson.getId()))
                .header("Content-Type", "application/json")
                .entity(payload)
                .build();
    }

    @GET
    @Path("/{id}/lessons")
    public Response getLessonsByCourseId(@PathParam("id") Long id){
        Optional<Course> possibleCourse;
        possibleCourse = courseRepository.findByIdOptional(id);

        if(possibleCourse.isEmpty()){
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        Course course = possibleCourse.get();

        List<LessonResponse> response = course.getLessons().stream().map(lesson -> new LessonResponse(lesson.getId(), lesson.getName())).toList();

        return Response.ok(response).build();
    }
}
