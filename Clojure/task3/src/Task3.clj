(ns Task3)

(defn pfilter
  ([pred coll]
   (let [num-cores (.availableProcessors (Runtime/getRuntime))
         chunk-size 20
         chunks (map doall (partition-all chunk-size coll))
         process-chunk #(future (doall (filter pred %)))
         futures (map process-chunk chunks)
         combine-results (fn combine-results [vs fs]
                           (if-let [remaining (seq fs)]
                             (lazy-seq (lazy-cat (deref (first vs))
                                                 (combine-results (rest vs) (rest remaining))))
                             (apply concat (map deref vs))))]
     (combine-results futures (drop num-cores futures)))))
