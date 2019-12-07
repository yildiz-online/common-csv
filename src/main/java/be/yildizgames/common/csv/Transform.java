/*
 * Copyright (C) Grégory Van den Borre - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Grégory Van den Borre <vandenborre.gregory@hotmail.com> 2019
 */
package be.yildizgames.common.csv;

import org.apiguardian.api.API;

/**
 * Transform an input for CSV.
 * @author Grégory Van den Borre
 */
public interface Transform {

    @API(status= API.Status.STABLE)
    String to(String origin);

}
