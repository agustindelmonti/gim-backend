package gym.controllers;

import gym.model.User;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
public class UserSpecification implements Specification<User> {

    private final UserFilter filter;

    @Override
    public Predicate toPredicate(Root<User> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
        if (filter == null) return null;

        List<Predicate> filters = new ArrayList<>();

        if (filter.getName() != null) {
            filters.add(criteriaBuilder.like(root.get("name"), filter.getName() + "%"));
        }

        if (filter.getEmail() != null) {
            filters.add(criteriaBuilder.like(root.get("email"), "%" + filter.getEmail() + "%"));
        }

        if (filter.getType() != null) {
            filters.add(criteriaBuilder.equal(root.get("type"), filter.getType()));
        }

        return criteriaBuilder.and(filters.toArray(new Predicate[0]));
    }

    public Specification<User> build() {
        return Specification.where(this);
    }
}
