package com.bookstorestaticwebsite.StaticBookStoreWebsite.category;

import com.bookstorestaticwebsite.StaticBookStoreWebsite.admin.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    public List<Category> getAllCategories(){return categoryRepository.findAll();}

    public Category getCategoryById(int id){
        return categoryRepository.findById(id).orElse(null);
    }

    public void createNewCategory(Category category){
        if(category != null) {
            if (checkCategoryExist(category)) {
                System.out.println("Already exist");
                return;
            }
            category = new Category(category.getCategoryName());
            categoryRepository.save(category);
        }
    }

    public void updateCategory(Category category, int id){
        Category existing = getCategoryById(id);
        existing.setCategoryName(category.getCategoryName());
        categoryRepository.save(existing);
    }



    public void deleteCategoryById(int id) { categoryRepository.deleteById(id);
    }

    //check email already exists or not
    public boolean checkCategoryExist(Category category){
        boolean existed = false;
        List<Category> list_category = getAllCategories();
        for (Category c : list_category){
            if(category.getCategoryName().equals(c.getCategoryName())){
                existed = true;
                break;
            }
        }
        return existed;
    }




}
